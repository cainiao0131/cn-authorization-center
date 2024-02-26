package org.cainiao.authorizationcenter.config.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import static org.cainiao.authorizationcenter.config.FilterChainOrder.FORM_LOGIN_PRECEDENCE;
import static org.cainiao.authorizationcenter.config.resourceserver.ResourceServerConfig.RESOURCE_SERVER_REQUEST_MATCHER;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AuthenticationConfig {

    @Bean
    @Order(FORM_LOGIN_PRECEDENCE)
    SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(new NegatedRequestMatcher(RESOURCE_SERVER_REQUEST_MATCHER))
            .authorizeHttpRequests(authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer
                .anyRequest().authenticated())
            // 处理从授权服务器 Filter Chain 到登录页的重定向的表单登录
            .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

}
