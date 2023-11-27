package org.cainiao.authorization.config.security;

import org.cainiao.authorization.service.organization.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 配置 Web 安全<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return new UserInfoDetailsService(userService);
    }

}
