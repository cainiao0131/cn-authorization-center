package org.cainiao.authorizationcenter.dao.repository.acl.organization;

import org.cainiao.authorizationcenter.entity.acl.organization.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface TenantUserRepository extends JpaRepository<TenantUser, Long> {

    boolean existsByDeletedFalseAndTenantIdAndUserId(long tenantId, long userId);
}
