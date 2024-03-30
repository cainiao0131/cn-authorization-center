package org.cainiao.authorizationcenter.entity.acl.organization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * 表示用户属于哪些租户<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_tenant_user")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -8073685222904689538L;

    /**
     * 租户ID
     */
    @Column(name = "teu_tenant_id", nullable = false)
    private long tenantId;

    /**
     * 平台用户ID
     */
    @Column(name = "teu_user_id", nullable = false)
    private long userId;
}
