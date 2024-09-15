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

    /**
     * 当授权服务器作为 OAuth2 客户端调用三方 API 时（如飞书）<br />
     * 需要在发请求前在 HTTP Header 上设置 access token
     *
     * @param accessTokenRepository AccessTokenRepository
     * @return WebClient
     */
    @Bean
    WebClient webClient() {
        return WebClient.builder().build();
    }
}
