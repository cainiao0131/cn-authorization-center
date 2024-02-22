package org.cainiao.authorizationcenter.config.thirdpartyapi;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.cainiao.api.lark.LarkApi;
import org.cainiao.api.lark.impl.LarkApiImpl;
import org.cainiao.api.lark.impl.authenticateandauthorize.getaccesstokens.CacheableGetAccessTokens;
import org.cainiao.api.lark.impl.authenticateandauthorize.getaccesstokens.RestTemplateGetAccessTokens;
import org.cainiao.api.lark.impl.service.RestTemplateService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * TODO 将 api 包独立为单独的项目后，进行 @AutoConfiguration 自动配置<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class LarkApiConfig {

    /**
     * 默认自动配置一个有缓存的飞书 API
     *
     * @param cacheableGetAccessTokens 有缓存的 GetAccessTokens
     * @return 有缓存的飞书 API
     */
    @Bean
    public LarkApi larkApi(CacheableGetAccessTokens cacheableGetAccessTokens) {
        return new LarkApiImpl(cacheableGetAccessTokens);
    }

    /**
     * 为了让 @Cacheable 的 AOP 生效，CacheableGetAccessTokens 必须是 @Bean<br />
     * 由于是自动配置的一部分，不要基于组件扫描，即不要用 @Component 这类基于自动扫描的注解，详见 Spring AutoConfiguration 官网
     *
     * @param restTemplateService RestTemplateService
     * @return CacheableGetAccessTokens
     */
    @Bean
    public CacheableGetAccessTokens cacheableRestTemplateGetAccessTokens(RestTemplateService restTemplateService) {
        return new CacheableGetAccessTokens(new RestTemplateGetAccessTokens(restTemplateService));
    }

    @Bean
    public RestTemplateService restTemplateService(RestTemplate restTemplate) {
        return new RestTemplateService(restTemplate);
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        /*
         * TODO 过期时间动态根据响应中的值更新
         * 获取 accessToken API 的缓存过期时间都是 2 小时，这里缓存过期时间我 2 小时减 1 分钟
         * 提前 1 分钟让缓存失效是为了避免因为时间不准确而导致用过期的 accessToken 缓存请求资源
         * 不同类型的 accessToken 的缓存过期时间是相同的，因此用同一个缓存：accessToken
         * 不同类型的 accessToken 一定对应不同的 appId，因为一个 appId 只可能有一个 accessToken 类型
         * 所以不用担心同一个缓存中不同 accessToken 的 Key 冲突
         */
        caffeineCacheManager.registerCustomCache("accessToken", Caffeine
            .newBuilder().expireAfterWrite(119, TimeUnit.MINUTES).build());
        return caffeineCacheManager;
    }

}
