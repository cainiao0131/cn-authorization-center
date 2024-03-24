package org.cainiao.authorizationcenter.config.httpclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
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
     * @param authorizedClientManager OAuth2AuthorizedClientManager
     * @return WebClient
     */
    @Bean
    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        /*
         * TODO 目前只考虑了只配置了一个飞书 OAuth2 客户端注册的情况
         *  有时间重构下，支持有多个客户端注册时动态选择用哪个客户端注册的 access token
         */
        ServletOAuth2AuthorizedClientExchangeFilterFunction
            exchangeFilterFunction = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        return WebClient.builder()
            // TODO 只有调用三方 API 的 URI 才需要设置 exchangeFilterFunction.defaultRequest()
            .defaultRequest(exchangeFilterFunction.defaultRequest())
            .filter(new DynamicExchangeFilterFunction(exchangeFilterFunction)).build();
    }
}
