package org.cainiao.authorization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("org.cainiao.authorization.mapper")
@EnableFeignClients
public class AuthorizationCenterApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(AuthorizationCenterApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}

}
