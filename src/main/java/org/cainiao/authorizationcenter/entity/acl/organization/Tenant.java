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
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_tenant")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Tenant extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -8073685222904689538L;

    /**
     * 租户名称
     */
    @Column(name = "ten_name", nullable = false)
    private String name;
}
