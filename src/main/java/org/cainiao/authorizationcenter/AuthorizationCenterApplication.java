package org.cainiao.authorizationcenter;

import org.cainiao.authorizationcenter.dao.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableCaching
@MapperScan(basePackageClasses = {UserMapper.class})
public class AuthorizationCenterApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AuthorizationCenterApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
}
