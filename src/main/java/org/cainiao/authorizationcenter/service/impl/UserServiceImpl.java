package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.acl.user.LarkUserRepository;
import org.cainiao.authorizationcenter.dao.repository.acl.user.UserRepository;
import org.cainiao.authorizationcenter.entity.acl.technology.SystemUser;
import org.cainiao.authorizationcenter.entity.acl.user.LarkUser;
import org.cainiao.authorizationcenter.entity.acl.user.User;
import org.cainiao.authorizationcenter.service.SystemUserService;
import org.cainiao.authorizationcenter.service.TenantUserService;
import org.cainiao.authorizationcenter.service.UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;

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

    private final UserRepository userRepository;
    private final LarkUserRepository larkUserRepository;
    private final SystemUserService systemUserService;
    private final TenantUserService tenantUserService;

    @Override
    @Transactional
    public void createIfFirstLogin(String clientId, OAuth2UserRequest userRequest, Map<String, Object> userAttributes) {
        // 平台用户 ID
        long cnUserId = -1;
        if (LARK_REGISTRATION_ID.equals(userRequest.getClientRegistration().getRegistrationId())) {
            // 通过【飞书】登录【平台】
            cnUserId = createIfFirstLarkLogin(userAttributes);
        }
        SystemUser systemUser = systemUserService.createIfFirstUse(cnUserId, clientId);
        tenantUserService.createIfFirstUse(cnUserId, systemUser.getSystemId());
        userAttributes.put("system_user_id", systemUser.getSystemUserId());
    }

    private long createIfFirstLarkLogin(Map<String, Object> userAttributes) {
        CnMap body = CnMap.toCnMap(userAttributes);
        Assert.notNull(body, "userAttributes cannot be null");
        String larkUserId = body.getString("user_id");
        long userId;
        // 查看这个飞书用户是否已经入库
        Optional<LarkUser> larkUserOptional = larkUserRepository.findByDeletedFalseAndLarkUserId(larkUserId);
        if (larkUserOptional.isPresent()) {
            userId = larkUserOptional.get().getUserId();
        } else {
            User user = userRepository.save(User.builder().build());
            userId = user.getId();
            larkUserRepository.save(LarkUser.builder()
                .userId(userId)
                .larkUserId(larkUserId)
                .openId(body.getString("open_id"))
                .unionId(body.getString("union_id"))
                .build());
        }
        return userId;
    }
}
