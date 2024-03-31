package org.cainiao.authorizationcenter.dao.repository.acl.technology;

import org.cainiao.authorizationcenter.entity.acl.technology.System;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface SystemRepository extends JpaRepository<System, Long> {

    Optional<System> findByDeletedFalseAndId(long id);
}
