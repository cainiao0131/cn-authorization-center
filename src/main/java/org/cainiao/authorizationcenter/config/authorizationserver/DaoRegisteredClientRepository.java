package org.cainiao.authorizationcenter.config.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.entity.JpaRegisteredClient;
import org.cainiao.authorizationcenter.service.RegisteredClientService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class DaoRegisteredClientRepository implements RegisteredClientRepository {

    private final RegisteredClientService registeredClientService;

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");

        JpaRegisteredClient jpaRegisteredClient = JpaRegisteredClient.from(registeredClient);

        JpaRegisteredClient oldOne = registeredClientService.findById(registeredClient.getId());
        if (oldOne == null) {
            assertUniqueIdentifiers(jpaRegisteredClient);
        } else {
            jpaRegisteredClient.setClientId(oldOne.getClientId()); // do not update client_id
            jpaRegisteredClient.setClientIdIssuedAt(oldOne.getClientIdIssuedAt()); // do not update client_id_issued_at
        }
        registeredClientService.save(jpaRegisteredClient);
    }

    private void assertUniqueIdentifiers(JpaRegisteredClient jpaRegisteredClient) {
        if (registeredClientService.existsByClientId(jpaRegisteredClient)) {
            throw new IllegalArgumentException("Registered client must be unique. " +
                "Found duplicate client identifier: " + jpaRegisteredClient.getClientId());
        }
        if (registeredClientService.existsByClientSecret(jpaRegisteredClient)) {
            throw new IllegalArgumentException("Registered client must be unique. " +
                "Found duplicate client secret for identifier: " + jpaRegisteredClient.getId());
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return registeredClientService.findRegisteredClientById(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return registeredClientService.findRegisteredClientByClientId(clientId);
    }
}
