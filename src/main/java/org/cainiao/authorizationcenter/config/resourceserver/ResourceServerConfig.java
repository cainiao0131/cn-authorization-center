package org.cainiao.authorizationcenter.config.resourceserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.cainiao.acl.core.component.AclUtil.customizeAuthorizeByAnnotation;
import static org.cainiao.authorizationcenter.config.FilterChainOrder.RESOURCE_SERVER_PRECEDENCE;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class ResourceServerConfig {

    public static final RequestMatcher RESOURCE_SERVER_REQUEST_MATCHER = new AntPathRequestMatcher("/tenant/**");

    @Bean
    @Order(RESOURCE_SERVER_PRECEDENCE)
    SecurityFilterChain resourceServerFilterChain(
        HttpSecurity http, RequestMappingHandlerMapping requestMappingHandlerMapping) throws Exception {

        http
            // 只会对 securityMatcher() 匹配的请求应用这个 SecurityFilterChain
            .securityMatcher(RESOURCE_SERVER_REQUEST_MATCHER)
            // 资源服务器不会渲染页面，无法在页面添加 CSRF Token，因此禁用
            .csrf(AbstractHttpConfigurer::disable)
            // 配置 BearerTokenAuthenticationFilter 解码 token 的算法
            .oauth2ResourceServer(oAuth2ResourceServerConfigurer ->
                oAuth2ResourceServerConfigurer.jwt(withDefaults()))
            // 配置 AuthorizationFilter，与 OAuth2 客户端的配置本质上是相同的，只是授权服务器只处理 scopes 权限
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                customizeAuthorizeByAnnotation(requestMappingHandlerMapping, authorizationManagerRequestMatcherRegistry,
                    (authorizedUrl, cnACL) -> {
                        String[] authorities = Stream
                            .concat(Arrays.stream(cnACL.scopes()).map(scope -> String.format("SCOPE_%s", scope)),
                                Stream.concat(Stream.of(cnACL.authorities()), Stream.of(cnACL.value()))).toArray(String[]::new);
                        if (authorities.length > 0) {
                            authorizedUrl.hasAnyAuthority(authorities);
                        } else {
                            authorizedUrl.authenticated();
                        }
                    });
                authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
            });
        return http.build();
    }
}
