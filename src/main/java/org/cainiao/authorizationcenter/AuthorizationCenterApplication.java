package org.cainiao.authorizationcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableCaching
@EnableJpaRepositories("org.cainiao.authorizationcenter.dao.repository")
public class AuthorizationCenterApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AuthorizationCenterApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}

}
