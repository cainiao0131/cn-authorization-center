package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.acl.organization.TenantUserRepository;
import org.cainiao.authorizationcenter.dao.repository.acl.technology.SystemRepository;
import org.cainiao.authorizationcenter.entity.acl.organization.TenantUser;
import org.cainiao.authorizationcenter.entity.acl.technology.System;
import org.cainiao.authorizationcenter.service.TenantUserService;
import org.cainiao.common.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class TenantUserServiceImpl implements TenantUserService {

    private final TenantUserRepository tenantUserRepository;
    private final SystemRepository systemRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createIfFirstUse(long userId, long systemId) {
        Optional<System> systemOptional = systemRepository.findByDeletedFalseAndId(systemId);
        if (systemOptional.isPresent()) {
            long tenantId = systemOptional.get().getTenantId();
            if (!tenantUserRepository.existsByDeletedFalseAndTenantIdAndUserId(tenantId, userId)) {
                tenantUserRepository.save(TenantUser.builder()
                    .tenantId(tenantId)
                    .userId(userId)
                    .tenantUserId(RandomUtil.UU32()).build());
            }
        }
    }
}
