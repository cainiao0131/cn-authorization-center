package org.cainiao.authorizationcenter.entity.acl.environment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * 环境，如开发环境、生产环境<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_environment")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Environment extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -3684582002496716430L;

    /**
     * 环境名称，英文简称，见名知意，会用于拼接某些规范名称
     */
    @Column(name = "env_name", nullable = false, unique = true)
    private String name;

    /**
     * 环境描述
     */
    @Column(name = "env_description", nullable = false, unique = true)
    private String description;
}
