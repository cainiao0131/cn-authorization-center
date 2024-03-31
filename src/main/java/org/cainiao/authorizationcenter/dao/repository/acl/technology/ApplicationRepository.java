package org.cainiao.authorizationcenter.dao.repository.acl.technology;

import org.cainiao.authorizationcenter.entity.acl.technology.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByDeletedFalseAndId(long id);
}
