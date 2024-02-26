package org.cainiao.authorizationcenter.dao.repository;

import org.cainiao.authorizationcenter.entity.acl.Application;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface ApplicationRepository extends JpaRepository<Application, String> {

}
