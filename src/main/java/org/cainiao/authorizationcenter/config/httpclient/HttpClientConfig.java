package org.cainiao.authorizationcenter.config.httpclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

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

    @RequiredArgsConstructor
    public static class DynamicExchangeFilterFunction implements ExchangeFilterFunction {

        private final String baseUrl;
        private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
        private final Map<String, Set<String>> registrationIdUrisMap;

        @Override
        @NonNull
        public Mono<ClientResponse> filter(@NonNull ClientRequest request, @NonNull ExchangeFunction next) {
            String url = request.url().toString();
            for (Map.Entry<String, Set<String>> registrationIdUrisEntry : registrationIdUrisMap.entrySet()) {
                if (needOauth2Authorization(url, registrationIdUrisEntry.getValue())) {
                    return accessTokenFilter(request, next, registrationIdUrisEntry.getKey());
                }
            }
            return next.exchange(request);
        }

        /**
         * 为所需的 Scope 加上对应 OAuth2 客户端注册的 access token
         *
         * @param request              ClientRequest
         * @param next                 ExchangeFunction
         * @param clientRegistrationId OAuth2 客户端注册 ID
         * @return Mono<ClientResponse>
         */
        private Mono<ClientResponse> accessTokenFilter(@NonNull ClientRequest request,
                                                       @NonNull ExchangeFunction next, String clientRegistrationId) {
            OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest
                .withClientRegistrationId(clientRegistrationId)
                .principal(SecurityContextHolder.getContext().getAuthentication())
                .build());
            Assert.notNull(authorizedClient, "authorizedClient cannot be null");
            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            return next.exchange(ClientRequest.from(request)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .build());
        }

        /**
         * 是否需要 OAuth2 授权<br />
         * 如果需要，且当前 OAuth2 客户端没有授权会重定向到 OAuth2 授权流程<br />
         * TODO 应该根据技术中台元数据进行配置，因为不仅仅是授权中心的资源服务器需要 OAuth2 授权
         *  另外还要考虑只需要客户端鉴权的情况，另外那些不需要授权的端点，应该排除
         *  那些 URI 需要授权应该根据【服务编排】决定
         *
         * @param url  准备请求的目标 URI
         * @param uris 需要某OAuth2注册的access token的 URI 集合
         * @return 是否需要 OAuth2 授权
         */
        private boolean needOauth2Authorization(String url, Set<String> uris) {
            return uris.stream().anyMatch(uri -> url.startsWith(String.format("%s/%s", baseUrl, uri)));
        }
    }
}
