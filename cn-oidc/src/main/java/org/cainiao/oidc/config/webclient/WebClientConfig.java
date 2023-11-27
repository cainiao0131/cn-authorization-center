package org.cainiao.oidc.config.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class WebClientConfig {

    @Bean
    WebClientService webClient() {
        return new WebClientService(WebClient.builder()
            /*
             * 如果请求时设置的 URI 是相对路径，则会基于此 baseUrl 构建 URL
             * 如果请求时设置的是绝对路径则会忽略此 baseUrl
             */
            .baseUrl("http://127.0.0.1/mock")
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)))))
            .build());
    }

}
