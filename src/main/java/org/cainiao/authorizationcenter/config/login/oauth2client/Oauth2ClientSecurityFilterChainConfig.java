package org.cainiao.authorizationcenter.config.login.oauth2client;

import org.cainiao.api.lark.imperative.LarkApi;
import org.cainiao.authorizationcenter.config.login.oauth2client.httpclient.lark.LarkMapOAuth2AccessTokenResponseConverter;
import org.cainiao.authorizationcenter.config.login.oauth2client.tokenendpoint.DynamicAuthorizationCodeTokenResponseClient;
import org.cainiao.authorizationcenter.config.login.oauth2client.tokenendpoint.lark.LarkOAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.cainiao.authorizationcenter.config.login.oauth2client.userinfoendpoint.DynamicOAuth2UserService;
import org.cainiao.authorizationcenter.service.UserService;
import org.cainiao.oauth2.client.core.dao.service.CnClientRegistrationMapperService;
import org.cainiao.oauth2.client.core.filter.ForceHttpsPortAndSchemeFilter;
import org.cainiao.oauth2.client.core.properties.CNOAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.cainiao.authorizationcenter.config.FilterChainOrder.OAUTH2_CLIENT_LOGIN_PRECEDENCE;
import static org.cainiao.authorizationcenter.config.resourceserver.ResourceServerConfig.RESOURCE_SERVER_REQUEST_MATCHER;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
@EnableConfigurationProperties(CNOAuth2ClientProperties.class)
public class Oauth2ClientSecurityFilterChainConfig {

    public static final String LARK_REGISTRATION_ID = "cn-lark-client";

    @Bean
    RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    @Order(OAUTH2_CLIENT_LOGIN_PRECEDENCE)
    SecurityFilterChain oauth2ClientLoginFilterChain(HttpSecurity http,
                                                     ClientRegistrationRepository clientRegistrationRepository,
                                                     LarkApi larkApi,
                                                     UserService userService,
                                                     CNOAuth2ClientProperties properties) throws Exception {
        RestOperations larkRestTemplate = getLarkRestOperations();

        http
            // 只会对 securityMatcher() 匹配的请求应用这个 SecurityFilterChain
            .securityMatcher(new NegatedRequestMatcher(RESOURCE_SERVER_REQUEST_MATCHER))
            .authorizeHttpRequests(authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                .anyRequest().authenticated())
            .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
                    .authorizationRequestResolver(oAuth2AuthorizationRequestResolver(clientRegistrationRepository)))
                .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig
                    // OAuth2AccessTokenResponseClient 可以通过 @Bean 进行自定义，这里为了不影响其它 FilterChain，直接构造
                    .accessTokenResponseClient(new DynamicAuthorizationCodeTokenResponseClient()
                        .registerDelegateConverter(LARK_REGISTRATION_ID,
                            new LarkOAuth2AuthorizationCodeGrantRequestEntityConverter(larkApi))
                        .registerRestOperations(LARK_REGISTRATION_ID, larkRestTemplate)))
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                    // OAuth2UserService 可以通过 @Bean 进行自定义，这里为了不影响其它 FilterChain，直接构造
                    .userService(new DynamicOAuth2UserService(userService)
                        .registerRestOperations(LARK_REGISTRATION_ID, larkRestTemplate))))
            .oauth2Client(withDefaults());
        if (properties.isForceHttps()) {
            http.addFilterBefore(new ForceHttpsPortAndSchemeFilter(), SecurityContextHolderFilter.class);
        }
        return http.build();
    }

    /**
     * 如果没有配置 ClientRegistrationRepository @Bean<br />
     * OAuth2ClientAutoConfiguration @Import 的 OAuth2ClientRegistrationRepositoryConfiguration 会根据<br />
     * OAuth2ClientProperties（spring.security.oauth2.client）<br />
     * 自动配置一个 InMemoryClientRegistrationRepository<br />
     */
    @Bean
    ClientRegistrationRepository clientRegistrationRepository(
        CnClientRegistrationMapperService cnClientRegistrationMapperService) {
        return new DaoClientRegistrationRepository(cnClientRegistrationMapperService);
    }

    /**
     * 配置解析器<br />
     * 根据当前请求路径参数中的 registrationId 解析授权中心的授权端点 URL，包括解析参数<br />
     * 这里之所以要自定义，是因为飞书的请求参数名称与默认值不同
     *
     * @param clientRegistrationRepository ClientRegistrationRepository
     * @return OAuth2AuthorizationRequestResolver
     */
    private OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver(
        ClientRegistrationRepository clientRegistrationRepository) {
        DefaultOAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver =
            new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,
                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
        oAuth2AuthorizationRequestResolver.setAuthorizationRequestCustomizer(oAuth2AuthorizationRequestBuilder ->
            oAuth2AuthorizationRequestBuilder.attributes(attributes -> {
                if (Optional.ofNullable(attributes.get("registration_id")).orElse("").toString()
                    .equals(LARK_REGISTRATION_ID)) {

                    // 飞书的 clientId 的 Query 参数名不是默认的 client_id 而是 app_id
                    oAuth2AuthorizationRequestBuilder.parameters(parameters -> {
                        if (parameters.containsKey(OAuth2ParameterNames.CLIENT_ID)) {
                            parameters.put("app_id", parameters.remove(OAuth2ParameterNames.CLIENT_ID));
                        }
                    });
                }
            }));
        return oAuth2AuthorizationRequestResolver;
    }

    private RestOperations getLarkRestOperations() {
        OAuth2AccessTokenResponseHttpMessageConverter
            oAuth2AccessTokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        oAuth2AccessTokenResponseHttpMessageConverter
            .setAccessTokenResponseConverter(new LarkMapOAuth2AccessTokenResponseConverter());

        // MappingJackson2HttpMessageConverter 放在最后，因为它能匹配到所有请求响应内容
        RestTemplate larkRestTemplate = new RestTemplate(Arrays.asList(
            oAuth2AccessTokenResponseHttpMessageConverter, new MappingJackson2HttpMessageConverter() {
                @Override
                @NonNull
                public Object read(@NonNull Type type, @Nullable Class<?> contextClass,
                                   @NonNull HttpInputMessage inputMessage)
                    throws IOException, HttpMessageNotReadableException {
                    Object result = super.read(type, contextClass, inputMessage);
                    Object data;
                    if (result instanceof Map<?, ?> resultMap && (data = resultMap.get("data")) != null) {
                        return data;
                    }
                    return result;
                }
            }));
        larkRestTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        return larkRestTemplate;
    }
}
