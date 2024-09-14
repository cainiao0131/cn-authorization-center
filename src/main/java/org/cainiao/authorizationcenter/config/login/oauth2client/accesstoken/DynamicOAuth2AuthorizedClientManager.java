package org.cainiao.authorizationcenter.config.login.oauth2client.accesstoken;

import org.cainiao.api.lark.imperative.LarkApi;
import org.cainiao.authorizationcenter.config.thirdpartyapi.lark.LarkAppAccessTokenRepository;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

/**
 * 为了定制 refresh token 刷新 access token 的行为<br />
 * 比如飞书没有提供 OIDC 配置元数据端点，刷新接口需要手动调用
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class DynamicOAuth2AuthorizedClientManager implements OAuth2AuthorizedClientManager {

    private final OAuth2AuthorizedClientManager delegate;

    public DynamicOAuth2AuthorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                OAuth2AuthorizedClientRepository authorizedClientRepository,
                                                LarkApi larkApi,
                                                LarkAppAccessTokenRepository larkAppAccessTokenRepository) {
        DefaultOAuth2AuthorizedClientManager delegate = new DefaultOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientRepository);
        delegate.setAuthorizedClientProvider(OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .clientCredentials()
            .provider(new RefreshTokenGrantBuilder()
                /*
                 * 加 5 分钟偏移
                 * 这样没有刷新的情况，一定离过期大于 5 分钟
                 * 此时拿到 access token 的人只要在 5 分钟内请求到三方资源服务器就不会因为网络延迟等因素导致过期
                 */
                .clockSkew(Duration.ofMinutes(5))
                .accessTokenResponseClient(
                    new DynamicDefaultRefreshTokenTokenResponseClient(larkApi, larkAppAccessTokenRepository))
                .build())
            .build());
        this.delegate = delegate;
    }

    @Override
    public OAuth2AuthorizedClient authorize(OAuth2AuthorizeRequest authorizeRequest) {
        return delegate.authorize(authorizeRequest);
    }

    public static class RefreshTokenGrantBuilder {

        private OAuth2AccessTokenResponseClient<OAuth2RefreshTokenGrantRequest> accessTokenResponseClient;

        private Duration clockSkew;

        private Clock clock;

        private RefreshTokenGrantBuilder() {
        }

        /**
         * Sets the client used when requesting an access token credential at the Token
         * Endpoint.
         *
         * @param accessTokenResponseClient the client used when requesting an access
         *                                  token credential at the Token Endpoint
         * @return the {@link RefreshTokenGrantBuilder}
         */
        public RefreshTokenGrantBuilder accessTokenResponseClient(
            OAuth2AccessTokenResponseClient<OAuth2RefreshTokenGrantRequest> accessTokenResponseClient) {
            this.accessTokenResponseClient = accessTokenResponseClient;
            return this;
        }

        /**
         * Sets the maximum acceptable clock skew, which is used when checking the access
         * token expiry. An access token is considered expired if
         * {@code OAuth2Token#getExpiresAt() - clockSkew} is before the current time
         * {@code clock#instant()}.
         *
         * @param clockSkew the maximum acceptable clock skew
         * @return the {@link RefreshTokenGrantBuilder}
         * @see RefreshTokenOAuth2AuthorizedClientProvider#setClockSkew(Duration)
         */
        public RefreshTokenGrantBuilder clockSkew(Duration clockSkew) {
            this.clockSkew = clockSkew;
            return this;
        }

        /**
         * Sets the {@link Clock} used in {@link Instant#now(Clock)} when checking the
         * access token expiry.
         *
         * @param clock the clock
         * @return the {@link RefreshTokenGrantBuilder}
         */
        public RefreshTokenGrantBuilder clock(Clock clock) {
            this.clock = clock;
            return this;
        }

        /**
         * Builds an instance of {@link RefreshTokenOAuth2AuthorizedClientProvider}.
         *
         * @return the {@link RefreshTokenOAuth2AuthorizedClientProvider}
         */
        public OAuth2AuthorizedClientProvider build() {
            RefreshTokenOAuth2AuthorizedClientProvider
                authorizedClientProvider = new RefreshTokenOAuth2AuthorizedClientProvider();
            if (this.accessTokenResponseClient != null) {
                authorizedClientProvider.setAccessTokenResponseClient(this.accessTokenResponseClient);
            }
            if (this.clockSkew != null) {
                authorizedClientProvider.setClockSkew(this.clockSkew);
            }
            if (this.clock != null) {
                authorizedClientProvider.setClock(this.clock);
            }
            return authorizedClientProvider;
        }
    }
}
