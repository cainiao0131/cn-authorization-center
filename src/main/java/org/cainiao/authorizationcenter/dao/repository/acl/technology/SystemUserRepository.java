package org.cainiao.authorizationcenter.dao.repository.acl.technology;

import org.cainiao.authorizationcenter.entity.acl.technology.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByDeletedFalseAndSystemIdAndUserId(long systemId, long userId);
}
