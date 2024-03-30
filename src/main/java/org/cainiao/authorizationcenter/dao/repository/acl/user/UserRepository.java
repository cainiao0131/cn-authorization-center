package org.cainiao.authorizationcenter.dao.repository.acl.user;

import org.cainiao.authorizationcenter.entity.acl.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
