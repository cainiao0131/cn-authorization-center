package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.service.LarkUserMapperService;
import org.cainiao.authorizationcenter.dao.service.UserMapperService;
import org.cainiao.authorizationcenter.entity.acl.LarkUser;
import org.cainiao.authorizationcenter.entity.acl.User;
import org.cainiao.authorizationcenter.service.UserService;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

import static org.cainiao.authorizationcenter.config.login.oauth2client.Oauth2ClientSecurityFilterChainConfig.LARK_REGISTRATION_ID;
import static org.cainiao.common.util.MapUtil.CnMap;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapperService userMapperService;
    private final LarkUserMapperService larkUserMapperService;

    @Override
    @Transactional
    public void createIfFirstLogin(OAuth2UserRequest userRequest, Map<String, Object> userAttributes) {
        if (userRequest.getClientRegistration().getRegistrationId().equals(LARK_REGISTRATION_ID)) {
            // 通过【飞书】登录【平台】，cn_user_id 为平台用户 ID
            userAttributes.put("cn_user_id", createIfFirstLarkLogin(userAttributes));
        } else {
            throw new BusinessException("非法登录方式");
        }
    }

    private long createIfFirstLarkLogin(Map<String, Object> userAttributes) {
        CnMap body = CnMap.toCnMap(userAttributes);
        Assert.notNull(body, "userAttributes cannot be null");
        String larkUserId = body.getString("user_id");
        LarkUser larkUser = larkUserMapperService.findByLarkUserId(larkUserId);
        if (larkUser != null) {
            // 曾经用飞书登录过，则直接返回关联的【开放平台用户 ID】
            return larkUser.getUserId();
        }
        User user = User.builder().build();
        if (!userMapperService.save(user)) {
            throw new BusinessException("保存用户失败");
        }
        long userId = user.getId();
        if (!larkUserMapperService.save(LarkUser.builder()
            .userId(userId)
            .larkUserId(larkUserId)
            .openId(body.getString("open_id"))
            .unionId(body.getString("union_id"))
            .build())) {

            throw new BusinessException("保存飞书用户失败");
        }
        return userId;
    }
}
