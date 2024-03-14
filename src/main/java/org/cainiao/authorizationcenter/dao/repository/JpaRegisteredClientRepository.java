package org.cainiao.authorizationcenter.dao.repository;

import org.cainiao.authorizationcenter.entity.authorizationserver.JpaRegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface JpaRegisteredClientRepository extends JpaRepository<JpaRegisteredClient, Long> {

    Optional<JpaRegisteredClient> findByRegisteredClientId(String registeredClientId);

    Optional<JpaRegisteredClient> findByClientId(String clientId);

    boolean existsByClientId(String clientId);

    boolean existsByClientSecret(String clientSecret);

}
