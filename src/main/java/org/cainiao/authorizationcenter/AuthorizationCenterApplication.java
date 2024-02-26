package org.cainiao.authorizationcenter;

import org.cainiao.oauth2.client.core.dao.repository.JpaClientRegistrationRepository;
import org.cainiao.oauth2.client.core.entity.JpaClientRegistration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableCaching
@EnableJpaRepositories(
	basePackages = {"org.cainiao.authorizationcenter.dao.repository"},
	basePackageClasses = {JpaClientRegistrationRepository.class}
)
@EntityScan(
	basePackages = {"org.cainiao.authorizationcenter.entity"},
	basePackageClasses = {JpaClientRegistration.class}
)
public class AuthorizationCenterApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AuthorizationCenterApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}

}
