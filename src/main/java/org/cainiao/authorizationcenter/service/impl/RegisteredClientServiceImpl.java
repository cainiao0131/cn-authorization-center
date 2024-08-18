package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.service.CnRegisteredClientMapperService;
import org.cainiao.authorizationcenter.entity.authorizationserver.CnRegisteredClient;
import org.cainiao.authorizationcenter.service.RegisteredClientService;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
public class RegisteredClientServiceImpl implements RegisteredClientService {

    private final CnRegisteredClientMapperService cnRegisteredClientMapperService;

    @Override
    public CnRegisteredClient findById(String id) {
        return cnRegisteredClientMapperService.findByRegisteredClientId(id);
    }

    @Override
    public RegisteredClient findRegisteredClientById(String id) {
        CnRegisteredClient cnRegisteredClient = cnRegisteredClientMapperService.findByRegisteredClientId(id);
        if (cnRegisteredClient == null) {
            return null;
        }
        return cnRegisteredClient.toRegisteredClient();
    }

    @Override
    public RegisteredClient findRegisteredClientByClientId(String clientId) {
        CnRegisteredClient cnRegisteredClient = cnRegisteredClientMapperService.findByClientId(clientId);
        if (cnRegisteredClient == null) {
            return null;
        }
        return cnRegisteredClient.toRegisteredClient();
    }

    @Override
    public boolean existsByClientId(CnRegisteredClient cnRegisteredClient) {
        return cnRegisteredClientMapperService.existsByClientId(cnRegisteredClient.getClientId());
    }

    @Override
    public boolean existsByClientSecret(CnRegisteredClient cnRegisteredClient) {
        return cnRegisteredClientMapperService.existsByClientSecret(cnRegisteredClient.getClientSecret());
    }

    @Override
    public CnRegisteredClient save(CnRegisteredClient cnRegisteredClient) {
        if (!cnRegisteredClientMapperService.save(cnRegisteredClient)) {
            throw new BusinessException("save CnRegisteredClient fail");
        }
        return cnRegisteredClient;
    }
}
