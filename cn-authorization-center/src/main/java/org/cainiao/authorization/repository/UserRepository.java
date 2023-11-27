package org.cainiao.authorization.repository;

import org.cainiao.authorization.entity.organization.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User findByUsername(String username);

    @Modifying
    @Query("update User u set u.password = ?1 where u.id = ?2")
    int setPasswordFor(String password, long id);

}
