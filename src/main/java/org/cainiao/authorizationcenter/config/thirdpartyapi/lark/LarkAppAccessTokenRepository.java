package org.cainiao.authorizationcenter.config.thirdpartyapi.lark;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.cainiao.api.lark.dto.response.authenticateandauthorize.getaccesstokens.AppAccessTokenResponse;
import org.cainiao.api.lark.imperative.LarkApiWithOutAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;
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

    private final LarkApiWithOutAccessToken larkApiWithOutAccessToken;
    private final static String SEPARATOR = "@_@";
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

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
                AppAccessTokenResponse appAccessTokenResponse = larkApiWithOutAccessToken
                    .authenticateAndAuthorizeWithOutAccessToken().getAccessTokensWithOutAccessToken()
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

    /**
     * 在 key 的粒度上同步一下
     * 避免对同一个客户端并发发起多个获取 app access token 的请求
     * 并发刷新，会导致只有最后一个刷新得到的 app access token 是有效的，其它的会失效
     * 虽然飞书做了优化来防止这种情况，短期内重复刷新会返回相同的 app access token
     * 但还是应该通过同步来尽量避免不必要的 I/O
     * <p>
     * TODO 没考虑分布式场景
     *
     * @param clientId     飞书 OAuth2 客户端 ID
     * @param clientSecret 飞书 OAuth2 客户端 Secret
     * @return 飞书 app access token
     */
    public String getCustomAppAppAccessToken(String clientId, String clientSecret) {
        String key = getCacheKey(clientId, clientSecret);
        synchronized (locks.computeIfAbsent(key, k -> new Object())) {
            try {
                return accessTokenCache.get(key);
            } catch (ExecutionException e) {
                LOGGER.error("accessTokenCache ExecutionException >>> ", e);
                return null;
            }
        }
    }

    private String getCacheKey(String clientId, String clientSecret) {
        return String.format("%s%s%s", clientId, SEPARATOR, clientSecret);
    }
}
