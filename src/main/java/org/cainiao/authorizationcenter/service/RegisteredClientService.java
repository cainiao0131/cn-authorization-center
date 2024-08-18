package org.cainiao.authorizationcenter.service;

import org.cainiao.authorizationcenter.entity.authorizationserver.CnRegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface RegisteredClientService {

    CnRegisteredClient findById(String registeredClientId);

    RegisteredClient findRegisteredClientById(String id);

    RegisteredClient findRegisteredClientByClientId(String clientId);

    boolean existsByClientId(CnRegisteredClient cnRegisteredClient);

    boolean existsByClientSecret(CnRegisteredClient cnRegisteredClient);

    CnRegisteredClient save(CnRegisteredClient cnRegisteredClient);
}
