package org.cainiao.authorizationcenter.service;

import org.cainiao.authorizationcenter.entity.authorizationserver.JpaRegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface RegisteredClientService {

    JpaRegisteredClient findById(String id);

    RegisteredClient findRegisteredClientById(String id);

    RegisteredClient findRegisteredClientByClientId(String clientId);

    boolean existsByClientId(JpaRegisteredClient jpaRegisteredClient);

    boolean existsByClientSecret(JpaRegisteredClient jpaRegisteredClient);

    JpaRegisteredClient save(JpaRegisteredClient jpaRegisteredClient);
}
