package org.cainiao.authorizationcenter.service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface UserService {

    /**
     * TODO 这里并未找到最好的方法，见：https://test-cprjkirb9nbd.feishu.cn/docx/AYZEdT9aVoGRchxdV8scsUf3nzd#DRAcdvbTCoLpVzx0WMHcg32In4g
     * <p>
     * 用户首次通过三方登录平台，自动注册平台用户<br />
     * 这里还没有为用户分配其在某【系统】中的【系统用户 ID】<br />
     * 在【应用】首次通过 OIDC 到授权中心获取用户信息时<br />
     * 为用户分配其在对应的【应用】所属的【系统】中的【系统用户 ID】<br />
     * 以及该用户在【系统】所属【租户】中的【租户用户 ID】<br />
     * OIDC user info 接口返回的用户信息中只包含【系统用户 ID】，以便系统间隔离，避免系统 A 的应用直接拿到了其在系统 B 中的用户标识<br />
     * 如果系统间调用涉及用户身份，应该用【租户用户 ID】来关联，且关联过程应该有授权中心来完成，过程中应用不应该拿到用户在别的系统中的 ID
     * <p>
     * 这里说的用户关联是指【应用】间调用，对于单点登录，不需要用户关联，不同【系统】可以在完全独立的用户体系下完成【系统】间单点登录<br />
     * 因为各【系统】共享【授权中心】会话<br />
     * 如果【遗留系统】迁移到平台时，【遗留系统】中的资源关联的用户标识是工号，而工号在平台中被设置为【租户用户 Key】<br />
     * 【应用】可一次性或逐步将资源中用于关联的工号换为用户在这个【系统】中的【系统用户 ID】<br />
     * 可以扫描所有用到的工号，通过授权中心的【租户用户 Key】换【系统用户 ID】进行替换<br />
     * 这个接口会对客户端进行 OAuth2 客户端凭据鉴权，如果用户在该客户端对应的【应用】所属的【系统】中没有【系统用户 ID】，则会立即创建
     * <p>
     * 用户的【特定于系统的资源】应该在数据库表中用【系统用户 ID】来标识其归属用户，而不是用【平台用户 ID】<br />
     * 这样用户访问其在某【系统】中的资源时，不需要到授权中心获取平台用户 ID，而是只需要通过【系统用户 ID】获取属于自己的资源
     *
     * @param userRequest    OAuth2UserRequest
     * @param userAttributes 最终会被设置为 DefaultOAuth2User 的 attributes，且会被转换为不可变的 Map<br />
     *                       因此这里是最后可以将平台用户 ID 设置到授权中心鉴权主体中的机会
     */
    void createIfFirstLogin(OAuth2UserRequest userRequest, Map<String, Object> userAttributes);
}
