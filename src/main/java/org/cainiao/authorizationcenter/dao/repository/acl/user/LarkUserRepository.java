package org.cainiao.authorizationcenter.dao.repository.acl.user;

import org.cainiao.authorizationcenter.entity.acl.user.LarkUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface LarkUserRepository extends JpaRepository<LarkUser, Long> {

    Optional<LarkUser> findByDeletedFalseAndLarkUserId(String larkUserId);
}
