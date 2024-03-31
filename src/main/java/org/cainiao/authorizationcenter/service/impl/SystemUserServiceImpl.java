package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.acl.technology.SystemUserRepository;
import org.cainiao.authorizationcenter.entity.acl.technology.Application;
import org.cainiao.authorizationcenter.entity.acl.technology.SystemUser;
import org.cainiao.authorizationcenter.service.ApplicationService;
import org.cainiao.authorizationcenter.service.SystemUserService;
import org.cainiao.common.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final ApplicationService applicationService;
    private final SystemUserRepository systemUserRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SystemUser createIfFirstUse(long userId, String clientId) {
        Application application = applicationService.findByClientId(clientId);
        Assert.notNull(application, "application cannot be null");
        long systemId = application.getSystemId();
        return systemUserRepository.findByDeletedFalseAndSystemIdAndUserId(systemId, userId)
            .orElseGet(() -> systemUserRepository.save(SystemUser.builder()
                .systemId(systemId)
                .userId(userId)
                .systemUserId(RandomUtil.UU32()).build()));

    }
}
