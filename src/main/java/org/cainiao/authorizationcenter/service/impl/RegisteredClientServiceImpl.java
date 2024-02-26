package org.cainiao.authorizationcenter.service.impl;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.JpaRegisteredClientRepository;
import org.cainiao.authorizationcenter.entity.authorizationserver.JpaRegisteredClient;
import org.cainiao.authorizationcenter.service.RegisteredClientService;
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

    private final JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @Override
    public JpaRegisteredClient findById(String id) {
        return jpaRegisteredClientRepository.findById(id).orElse(null);
    }

    @Override
    public RegisteredClient findRegisteredClientById(String id) {
        return jpaRegisteredClientRepository.findById(id).map(JpaRegisteredClient::toRegisteredClient).orElse(null);
    }

    @Override
    public RegisteredClient findRegisteredClientByClientId(String clientId) {
        return jpaRegisteredClientRepository.findByClientId(clientId)
            .map(JpaRegisteredClient::toRegisteredClient).orElse(null);
    }

    @Override
    public boolean existsByClientId(JpaRegisteredClient jpaRegisteredClient) {
        return jpaRegisteredClientRepository.existsByClientId(jpaRegisteredClient.getClientId());
    }

    @Override
    public boolean existsByClientSecret(JpaRegisteredClient jpaRegisteredClient) {
        return jpaRegisteredClientRepository.existsByClientSecret(jpaRegisteredClient.getClientSecret());
    }

    @Override
    public JpaRegisteredClient save(JpaRegisteredClient jpaRegisteredClient) {
        return jpaRegisteredClientRepository.save(jpaRegisteredClient);
    }
}
