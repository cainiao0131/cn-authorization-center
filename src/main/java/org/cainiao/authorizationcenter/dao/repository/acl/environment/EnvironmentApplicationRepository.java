package org.cainiao.authorizationcenter.dao.repository.acl.environment;

import org.cainiao.authorizationcenter.entity.acl.environment.EnvironmentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface EnvironmentApplicationRepository extends JpaRepository<EnvironmentApplication, Long> {

    Optional<EnvironmentApplication> findByDeletedFalseAndClientId(String clientId);
}
