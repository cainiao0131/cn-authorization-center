package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.service.CnRegisteredClientMapperService;
import org.cainiao.authorizationcenter.dto.request.OAuth2Client;
import org.cainiao.authorizationcenter.entity.authorizationserver.CnRegisteredClient;
import org.cainiao.authorizationcenter.service.RegisteredClientService;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

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
    public void registerClient(OAuth2Client client) {
        // TODO 从鉴权主体中获取 tenantId、userName
        String userName = "cainiao";
        long tenantId = 1;

        String clientId = client.getClientId();
        if (!StringUtils.hasText(clientId)) {
            throw new BusinessException("clientId is invalid");
        }
        if (cnRegisteredClientMapperService.existsByClientId(clientId)) {
            throw new BusinessException("clientId already exists");
        }
        client.setRegisteredClientId(UUID.randomUUID().toString());
        client.setTenantId(tenantId);
        client.setCreatedBy(userName);
        client.setUpdatedBy(userName);
        if (!cnRegisteredClientMapperService.save(client)) {
            throw new BusinessException("save OAuth2Client fail");
        }
    }

    @Override
    public void updateClient(String clientId, OAuth2Client client) {

    }

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
