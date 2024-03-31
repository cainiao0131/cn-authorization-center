package org.cainiao.authorizationcenter.entity.acl.technology;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * TODO 考虑创建一个授权中心的库，将部分 OAuth2 Client 不用的类移过去<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_application")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class Application extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4505057660743337320L;

    /**
     * 所属系统ID
     */
    @Column(name = "app_system_id", nullable = false)
    private long systemId;

    /**
     * 应用名称
     */
    @Column(name = "app_name", nullable = false, unique = true)
    private String name;
}
