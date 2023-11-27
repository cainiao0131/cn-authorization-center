package org.cainiao.oidc.config.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient webClient;

    public <T> T get(String url, Class<T> type) {
        return webClient.get().uri(url).retrieve().bodyToMono(type).block();
    }

    public <T> T get(String url, String headerKey, String headerValue, Class<T> type) {
        return webClient.get().uri(url).header(headerKey, headerValue).retrieve().bodyToMono(type).block();
    }

    public <T> T post(String url, BodyInserter<Map<String, Object>, ReactiveHttpOutputMessage> body, Class<T> type) {
        return webClient.post().uri(url).header("Content-Type", "application/json")
            .body(body).retrieve().bodyToMono(type).block();
    }

    public BodyInserterBuilder postBody(String key, Object value) {
        BodyInserterBuilder bodyInserterBuilder = postBody();
        bodyInserterBuilder.put(key, value);
        return bodyInserterBuilder;
    }

    public BodyInserterBuilder postBody() {
        return new BodyInserterBuilder(new HashMap<>(), this);
    }

    @RequiredArgsConstructor
    public static class BodyInserterBuilder {
        private final Map<String, Object> requestBody;
        private final WebClientService webClientService;

        public BodyInserterBuilder put(String key, Object value) {
            requestBody.put(key, value);
            return this;
        }

        public <T> T post(String url, Class<T> type) {
            return webClientService.post(url, build(), type);
        }

        public BodyInserter<Map<String, Object>, ReactiveHttpOutputMessage> build() {
            return BodyInserters.fromValue(requestBody);
        }
    }

}
