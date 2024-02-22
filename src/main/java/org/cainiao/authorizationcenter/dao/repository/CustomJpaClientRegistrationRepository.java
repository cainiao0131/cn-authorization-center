package org.cainiao.authorizationcenter.dao.repository;

import org.cainiao.oauth2.client.core.dao.repository.JpaClientRegistrationRepository;
import org.cainiao.oauth2.client.core.entity.JpaClientRegistration;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface CustomJpaClientRegistrationRepository extends JpaClientRegistrationRepository {

    Optional<JpaClientRegistration> findByRegistrationId(String registrationId);
}
