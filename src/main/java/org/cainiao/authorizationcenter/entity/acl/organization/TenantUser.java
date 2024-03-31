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

    /**
     * 用户在这个租户中的唯一标识<br />
     * 租户内的资源应该以这个 ID 来标识资源由哪个用户所有，而不是用平台用户ID<br />
     * 已达到用户ID在租户间隔离的目的，并且可用于将同一个租户内的多个【系统用户ID】关联起来（类似飞书的union_id）
     */
    @Column(name = "teu_tenant_user_id", nullable = false)
    private String tenantUserId;
}
