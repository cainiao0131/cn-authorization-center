package org.cainiao.authorizationcenter.config.authorizationserver;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.cainiao.authorizationcenter.service.RegisteredClientService;
import org.cainiao.authorizationcenter.service.SystemUserService;
import org.cainiao.authorizationcenter.service.TenantUserService;
import org.cainiao.oauth2.client.core.filter.ForceHttpsPortAndSchemeFilter;
import org.cainiao.oauth2.client.core.properties.CNOAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;

import static org.cainiao.authorizationcenter.config.FilterChainOrder.AUTHORIZATION_SERVER_PRECEDENCE;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 授权服务器元数据端点：/.well-known/oauth-authorization-server
 * 由 OAuth2AuthorizationServerMetadataEndpointFilter 提供
 */
@Configuration
public class AuthorizationServerConfig {

    /**
     * 如果没有 SecurityFilterChain @Bean
     * OAuth2AuthorizationServerAutoConfiguration @Import 的 OAuth2AuthorizationServerWebSecurityConfiguration
     * 会自动配置一个优先级为 @Order(Ordered.HIGHEST_PRECEDENCE) 的 SecurityFilterChain
     */
    @Bean
    @Order(AUTHORIZATION_SERVER_PRECEDENCE)
    SecurityFilterChain authorizationServerFilterChain(HttpSecurity http,
                                                       CNOAuth2ClientProperties properties,
                                                       SystemUserService systemUserService,
                                                       TenantUserService tenantUserService) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            // 启用 OpenID Connect 1.0
            .oidc(oidcConfigurer -> oidcConfigurer
                .userInfoEndpoint(oidcUserInfoEndpointConfigurer -> oidcUserInfoEndpointConfigurer
                    .userInfoMapper(new DynamicOidcUserInfoMapper(systemUserService, tenantUserService))));
        // OidcUserInfoEndpointConfigurer
        http
            // Accept access tokens for User Info and/or Client Registration
            .oauth2ResourceServer(oAuth2ResourceServerConfigurer -> oAuth2ResourceServerConfigurer
                .jwt(withDefaults()))
            // 未鉴权则重定向到登录页，用自定义的 DynamicAuthenticationEntryPoint 实现动态确定登录页
            .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                .defaultAuthenticationEntryPointFor(new DynamicAuthenticationEntryPoint("/login"),
                    createRequestMatcher()));
        if (properties.isForceHttps()) {
            http.addFilterBefore(new ForceHttpsPortAndSchemeFilter(), SecurityContextHolderFilter.class);
        }
        return http.build();
    }

    private static RequestMatcher createRequestMatcher() {
        MediaTypeRequestMatcher requestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
        requestMatcher.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        return requestMatcher;
    }

    /**
     * 如果没有 RegisteredClientRepository @Bean
     * OAuth2AuthorizationServerAutoConfiguration @Import 的 OAuth2AuthorizationServerConfiguration 会根据
     * OAuth2AuthorizationServerProperties（spring.security.oauth2.authorizationserver）
     * 自动配置一个 InMemoryRegisteredClientRepository
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(RegisteredClientService registeredClientService) {
        return new DaoRegisteredClientRepository(registeredClientService);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 如果没有 AuthorizationServerSettings @Bean
     * OAuth2AuthorizationServerAutoConfiguration @Import 的 OAuth2AuthorizationServerConfiguration 会根据
     * OAuth2AuthorizationServerProperties（spring.security.oauth2.authorizationserver）
     * 自动配置一个 AuthorizationServerSettings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
            /*
             * 发布者标识符（issuer identifier），如没有配置则会从当前请求中解析
             * 如果配置了，则 /.well-known/oauth-authorization-server 或 /.well-known/openid-configuration
             * 公布的所有端点会加上这个前缀
             * 这里只能到域名，不能加 URI，否则会报错，因此无法用 URI 来区分授权服务器与客户端服务
             * 另外本来授权服务器与客户端也不能在同一个域名，因为那样会导致会话互相干扰，所以应该为授权服务器设置一个单独的域名
             *
             * 这里虽然不配置 issuer 也会以请求时的域名为准，这样更灵活，可以兼容域名变化的情况
             * 但是，如果 TLS 终止是在更外层完成的，那么应用收到的是 HTTP 请求，这会导致自动生成的域名为 HTTP，导致问题
             */
            .issuer("https://auth.mcainiaom.top")
            .build();
    }

}
