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

import static org.cainiao.acl.core.util.HasScopeUtil.customizeScopeAuthorizeByHasScope;
import static org.cainiao.authorizationcenter.config.FilterChainOrder.RESOURCE_SERVER_PRECEDENCE;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class ResourceServerConfig {

    public static final RequestMatcher RESOURCE_SERVER_REQUEST_MATCHER = new AntPathRequestMatcher("/system/**");

    @Bean
    @Order(RESOURCE_SERVER_PRECEDENCE)
    SecurityFilterChain resourceServerFilterChain(
        HttpSecurity http, RequestMappingHandlerMapping requestMappingHandlerMapping) throws Exception {

        http
            // 只会对 securityMatcher() 匹配的请求应用这个 SecurityFilterChain
            .securityMatcher(RESOURCE_SERVER_REQUEST_MATCHER)
            // 资源服务器不会渲染页面，无法在页面添加 CSRF Token，因此禁用
            .csrf(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oAuth2ResourceServerConfigurer ->
                oAuth2ResourceServerConfigurer.jwt(withDefaults()))
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                customizeScopeAuthorizeByHasScope(requestMappingHandlerMapping, authorizationManagerRequestMatcherRegistry);
                authorizationManagerRequestMatcherRegistry.anyRequest().permitAll();
            });
        return http.build();
    }
}
