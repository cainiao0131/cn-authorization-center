package org.cainiao.authorizationcenter.config.thirdpartyapi.lark;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.cainiao.api.lark.dto.response.authenticateandauthorize.getaccesstokens.AppAccessTokenResponse;
import org.cainiao.api.lark.imperative.LarkApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
@RequiredArgsConstructor
public class LarkAppAccessTokenRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(LarkAppAccessTokenRepository.class);

    private final LarkApi larkApi;
    private final static String SEPARATOR = "@_@";

    LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
        // 建议的更新操作线程数，不保证线程数一定是这里设置的值
        .concurrencyLevel(1)
        /*
         * app_access_token 的最大有效期是 2 小时
         * 如果在有效期小于 30 分钟的情况下调用接口，会返回一个新的 app_access_token，这会同时存在两个有效的 app_access_token
         * 如果在有效期大于等于 30 分钟的情况下调用接口，会返回原有的 app_access_token
         *
         * 缓存的逻辑是，离上一次调用接口超过 1 小时 50 分钟时，则失效
         * 之后如有调用，缓存未命中，会再次调用接口刷新 app_access_token
         * 这保证了所有从缓存获取的 app_access_token 在获取时其年龄都不超过 1 小时 50 分钟，所以至少还剩下 10 分钟的效期，且不会因为之后有人刷新而提前失效
         * 这个效期足以应对网络延迟，因为获取到 app_access_token 后都是立即使用的，不会持有等待一段时间后再使用
         */
        .expireAfterWrite(6600, TimeUnit.SECONDS)
        // 目前只存一个飞书的 app_access_token，容量 1 就够了
        .initialCapacity(1)
        // 缓存最大容量，超过后会按照 LRU 最近虽少使用算法淘汰
        .maximumSize(10)
        .build(new CacheLoader<>() {
            @Override
            public @Nonnull String load(@Nonnull String key) {
                String[] clientInfo = key.split(SEPARATOR);
                AppAccessTokenResponse appAccessTokenResponse = larkApi.authenticateAndAuthorize().getAccessTokens()
                    .getCustomAppAppAccessToken(clientInfo[0], clientInfo[1])
                    .getBody();
                Assert.notNull(appAccessTokenResponse, "appAccessTokenResponse cannot be null");
                String appAccessToken = appAccessTokenResponse.getAppAccessToken();
                Assert.notNull(appAccessToken, "appAccessToken cannot be null");
                return appAccessToken;
            }
        });

    public String getCustomAppAppAccessToken(ClientRegistration clientRegistration) {
        return getCustomAppAppAccessToken(clientRegistration.getClientId(), clientRegistration.getClientSecret());
    }

    public String getCustomAppAppAccessToken(String clientId, String clientSecret) {
        try {
            return accessTokenCache.get(getCacheKey(clientId, clientSecret));
        } catch (ExecutionException e) {
            LOGGER.error("accessTokenCache ExecutionException >>> ", e);
            return null;
        }
    }

    private String getCacheKey(String clientId, String clientSecret) {
        return String.format("%s%s%s", clientId, SEPARATOR, clientSecret);
    }
}
