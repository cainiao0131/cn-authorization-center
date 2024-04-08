package org.cainiao.authorizationcenter.config.httpclient;

import org.cainiao.oauth2.client.core.httpclient.DynamicExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
     * @param oAuth2AuthorizedClientManager OAuth2AuthorizedClientManager
     * @return WebClient
     */
    @Bean
    WebClient webClient(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        // TODO 暂时写死，应该配置到数据库中，并且最好与OAuth2客户端应用的配置统一，先走通流程
        Map<String, Set<String>> registrationIdUrisMap = new HashMap<>();
        Set<String> uris = new HashSet<>();
        uris.add("drive");
        uris.add("docx");
        registrationIdUrisMap.put("cn-lark-client", uris); // 飞书
        return WebClient.builder().filter(new DynamicExchangeFilterFunction("https://open.feishu.cn/open-apis",
                oAuth2AuthorizedClientManager, registrationIdUrisMap))
            .build();
    }
}
