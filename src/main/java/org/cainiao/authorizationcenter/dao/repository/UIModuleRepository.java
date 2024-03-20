package org.cainiao.authorizationcenter.dao.repository;

import org.cainiao.authorizationcenter.entity.acl.UIModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface UIModuleRepository extends JpaRepository<UIModule, Long> {

    Optional<UIModule> findByEnvironmentApplicationIdAndKey(long environmentApplicationId, String key);
}
