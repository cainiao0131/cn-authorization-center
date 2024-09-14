package org.cainiao.authorizationcenter.config.thirdpartyapi.lark;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.cainiao.api.lark.imperative.LarkApi;
import org.cainiao.api.lark.impl.imperative.WebClientLarkApi;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

/**
 * TODO 将 api 包独立为单独的项目后，进行 @AutoConfiguration 自动配置<br />
 * 不用飞书官方的 SDK 的原因：
 * <ol>
 *     <li>
 *         性能差<br />
 *         对响应的反序列化用的 com.google.gson.Gson 通用类库，没有针对特定类型优化，会增加运行时反射和没必要的判断的开销<br />
 *         且没提供扩展点对其进行替换<br />
 *         并且无法适配返回字段与飞书 API 字段不同的场景，例如【应用】调用【授权中心】的飞书代理资源服务时
 *     </li>
 *     <li>
 *         Token 相关逻辑不优雅<br />
 *         例如用户 Token 仍然需要应用程序自己维护并在每次调用接口时传入，导致 API 不干净<br />
 *         更期望将 Token 逻辑与 API 调用解耦，Token 逻辑由底层 HTTP 客户端实现，并与 Spring Security Oauth2 集成
 *     </li>
 *     <li>
 *         扩展性差<br />
 *         只支持 OKhttp 和 HttpClient，也没给个扩展接口注入其它实现
 *     </li>
 *     <li>
 *         不支持 Reactive 风格的 API<br />
 *         这会导致 WebFlux 的应用线程阻塞
 *     </li>
 *     <li>
 *         重试机制是写死的，获取 Token 失败重试 2 次，其它不重试，且不能配置
 *     </li>
 * </ol>
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class CustomerLarkApiConfig {

    /**
     * 默认自动配置一个有缓存的飞书 API
     *
     * @param restTemplate RestTemplate
     * @return 有缓存的飞书 API
     */
    @Bean
    public LarkApi larkApi(WebClient webClient) {
        return new WebClientLarkApi(webClient, "https://open.feishu.cn/open-apis");
    }

    /**
     * TODO 不用 CacheManager，用别的方案来限流
     */
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        /*
         * TODO 不应该用基于注解的缓存，应该基于一个缓存接口，便于扩展
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
