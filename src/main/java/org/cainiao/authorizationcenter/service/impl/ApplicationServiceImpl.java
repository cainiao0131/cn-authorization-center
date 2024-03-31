package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.acl.environment.EnvironmentApplicationRepository;
import org.cainiao.authorizationcenter.dao.repository.acl.technology.ApplicationRepository;
import org.cainiao.authorizationcenter.entity.acl.technology.Application;
import org.cainiao.authorizationcenter.service.ApplicationService;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final EnvironmentApplicationRepository environmentApplicationRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public Application findByClientId(String clientId) {
        return environmentApplicationRepository.findByDeletedFalseAndClientId(clientId)
            .flatMap(environmentApplication -> applicationRepository
                .findByDeletedFalseAndId(environmentApplication.getApplicationId())).orElse(null);
    }
}
