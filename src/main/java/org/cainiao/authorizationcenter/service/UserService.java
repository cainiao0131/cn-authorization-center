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
     *
     * @param userRequest    OAuth2UserRequest
     * @param userAttributes 最终会被设置为 DefaultOAuth2User 的 attributes，且会被转换为不可变的 Map<br />
     *                       因此这里是最后可以将平台用户 ID 设置到授权中心鉴权主体中的机会<br />
     *                       当用【三方（如飞书）】登录时，入参为【三方】返回的用户信息
     */
    void createIfFirstLogin(OAuth2UserRequest userRequest, Map<String, Object> userAttributes);
}
