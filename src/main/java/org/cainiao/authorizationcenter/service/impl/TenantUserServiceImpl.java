package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.service.SystemMapperService;
import org.cainiao.authorizationcenter.dao.service.TenantUserMapperService;
import org.cainiao.authorizationcenter.entity.acl.organization.TenantUser;
import org.cainiao.authorizationcenter.service.TenantUserService;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.common.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class TenantUserServiceImpl implements TenantUserService {

    private final TenantUserMapperService tenantUserMapperService;
    private final SystemMapperService systemMapperService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createIfFirstUse(long userId, long systemId) {
        long tenantId = systemMapperService.findById(systemId).getTenantId();
        if (!tenantUserMapperService.existsByTenantIdAndUserId(tenantId, userId)
            && !tenantUserMapperService.save(TenantUser.builder()
            .tenantId(tenantId).userId(userId).tenantUserId(RandomUtil.UU32()).build())) {

            throw new BusinessException("save TenantUser fail");
        }
    }
}
