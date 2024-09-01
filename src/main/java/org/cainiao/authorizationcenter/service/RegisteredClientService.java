package org.cainiao.authorizationcenter.service;

import org.cainiao.authorizationcenter.dto.request.OAuth2Client;
import org.cainiao.authorizationcenter.entity.authorizationserver.CnRegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface RegisteredClientService {

    void registerClient(OAuth2Client client);

    void updateClient(String clientId, OAuth2Client client);

    CnRegisteredClient findById(String registeredClientId);

    RegisteredClient findRegisteredClientById(String id);

    RegisteredClient findRegisteredClientByClientId(String clientId);

    boolean existsByClientId(CnRegisteredClient cnRegisteredClient);

    boolean existsByClientSecret(CnRegisteredClient cnRegisteredClient);

    CnRegisteredClient save(CnRegisteredClient cnRegisteredClient);
}
