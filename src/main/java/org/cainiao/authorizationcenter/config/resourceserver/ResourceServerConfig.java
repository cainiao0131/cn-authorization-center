package org.cainiao.authorizationcenter.config.resourceserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
    SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http
            // 只会对 securityMatcher() 匹配的请求应用这个 SecurityFilterChain
            .securityMatcher(RESOURCE_SERVER_REQUEST_MATCHER)
            .authorizeHttpRequests(authorizeHttpRequestsConfigurer ->
                // TODO 暂时都放开，后期根据不同的资源类型配置不同的规则
                authorizeHttpRequestsConfigurer.anyRequest().permitAll())
            .oauth2ResourceServer(oAuth2ResourceServerConfigurer ->
                oAuth2ResourceServerConfigurer.jwt(withDefaults()));
        return http.build();
    }
}
