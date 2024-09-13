package org.cainiao.authorizationcenter.config.httpclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

import static org.cainiao.authorizationcenter.config.login.oauth2client.Oauth2ClientSecurityFilterChainConfig.LARK_REGISTRATION_ID;

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
    WebClient webClient(AccessTokenRepository accessTokenRepository) {
        return WebClient.builder().filter(new DynamicExchangeFilterFunction(accessTokenRepository)).build();
    }

    @RequiredArgsConstructor
    public static class DynamicExchangeFilterFunction implements ExchangeFilterFunction {

        private static final Pattern larkUrlPattern = Pattern.compile("^https://open\\.feishu\\.cn/open-apis/(drive|docx)/.*");
        private final AccessTokenRepository accessTokenRepository;

        @Override
        @NonNull
        public Mono<ClientResponse> filter(@NonNull ClientRequest request, @NonNull ExchangeFunction exchangeFunction) {
            String url = request.url().toString();
            if (needLarkAccessToken(url)) {
                return exchangeFunction.exchange(ClientRequest.from(request)
                    .headers(headers -> headers.setBearerAuth(accessTokenRepository.getAccessToken(LARK_REGISTRATION_ID)))
                    .build());
            }
            return exchangeFunction.exchange(request);
        }

        private boolean needLarkAccessToken(String url) {
            return larkUrlPattern.matcher(url).matches();
        }
    }
}
