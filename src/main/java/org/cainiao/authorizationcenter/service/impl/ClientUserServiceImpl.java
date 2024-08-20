package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.service.ClientUserMapperService;
import org.cainiao.authorizationcenter.dao.service.SystemMapperService;
import org.cainiao.authorizationcenter.dao.service.TenantUserMapperService;
import org.cainiao.authorizationcenter.dao.service.UserSystemMapperService;
import org.cainiao.authorizationcenter.entity.acl.technology.System;
import org.cainiao.authorizationcenter.entity.authorizationserver.ClientUser;
import org.cainiao.authorizationcenter.service.ClientUserService;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.common.util.RandomUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.cainiao.authorizationcenter.entity.acl.technology.System.AccessScopeEnum;
import static org.cainiao.authorizationcenter.entity.acl.technology.System.AccessScopeEnum.SYSTEM_USER;
import static org.cainiao.authorizationcenter.entity.acl.technology.System.AccessScopeEnum.TENANT_USER;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class ClientUserServiceImpl implements ClientUserService {

    private final ClientUserMapperService clientUserMapperService;
    private final SystemMapperService systemMapperService;
    private final UserSystemMapperService userSystemMapperService;
    private final TenantUserMapperService tenantUserMapperService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ClientUser createIfFirstUse(long userId, String clientId) {
        System system = systemMapperService.findByClientId(clientId);
        if (system != null) {
            // 如果 OAuth2 客户端绑定了【系统】，那么需要校验当前用户是否可以访问该【系统】
            AccessScopeEnum accessScope = system.getAccessScope();
            if (accessScope != null) {
                long systemId = system.getId();
                if (accessScope == SYSTEM_USER) {
                    // 被授权访问该【系统】的用户才能访问
                    if (!userSystemMapperService.existsByUserIdAndSystemId(userId, systemId)) {
                        throw new AccessDeniedException("无权访问");
                    }
                } else if (accessScope == TENANT_USER) {
                    // 该【系统】所属【租户】的用户才能访问
                    if (!tenantUserMapperService.existsByTenantIdAndUserId(system.getTenantId(), userId)) {
                        throw new AccessDeniedException("无权访问");
                    }
                } else {
                    throw new BusinessException("System accessScope 数据异常");
                }
            }
        }
        ClientUser clientUser = clientUserMapperService.findByUserIdAndClientId(userId, clientId);
        if (clientUser == null) {
            clientUser = ClientUser.builder().userId(userId).clientId(clientId).openId(RandomUtil.UU32()).build();
            if (!clientUserMapperService.save(clientUser)) {
                throw new BusinessException("save TenantUser fail");
            }
        }
        return clientUser;
    }
}
