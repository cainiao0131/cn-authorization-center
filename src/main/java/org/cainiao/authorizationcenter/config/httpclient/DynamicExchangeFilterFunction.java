package org.cainiao.authorizationcenter.config.httpclient;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class DynamicExchangeFilterFunction implements ExchangeFilterFunction {

    private final ServletOAuth2AuthorizedClientExchangeFilterFunction delegate;

    @Override
    @NonNull
    public Mono<ClientResponse> filter(@NonNull ClientRequest request, @NonNull ExchangeFunction next) {
        if (needOauth2Authorization(request.url())) {
            return delegate.filter(request, next);
        }
        return next.exchange(request);
    }

    @Override
    @NonNull
    public ExchangeFilterFunction andThen(@NonNull ExchangeFilterFunction afterFilter) {
        return delegate.andThen(afterFilter);
    }

    @Override
    @NonNull
    public ExchangeFunction apply(@NonNull ExchangeFunction exchange) {
        return delegate.apply(exchange);
    }

    /**
     * 是否需要 OAuth2 授权<br />
     * 如果需要，且当前 OAuth2 客户端没有授权会重定向到 OAuth2 授权流程<br />
     *
     * @param uri 准备请求的目标 URI
     * @return 是否需要 OAuth2 授权
     */
    private boolean needOauth2Authorization(URI uri) {
        // TODO 这里只配置了目前需要的接口，有时间梳理一下哪些飞书接口需要 access token
        return uri.toString().startsWith("https://open.feishu.cn/open-apis/drive");
    }
}
