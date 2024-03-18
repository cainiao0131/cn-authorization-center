package org.cainiao.authorizationcenter.config.httpclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class HttpClientConfig {

    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
