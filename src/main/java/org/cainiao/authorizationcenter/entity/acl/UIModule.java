package org.cainiao.authorizationcenter.entity.acl;

import jakarta.persistence.*;
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
@Entity(name = "t_ui_module")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class UIModule extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -3395363283455945345L;

    /**
     * 父UI模块ID
     */
    @Column(name = "um_parent_id")
    private long parentId;

    /**
     * 环境应用 ID
     */
    @Column(name = "um_environment_application_id", nullable = false)
    private long environmentApplicationId;

    /**
     * UI模块名称
     */
    @Column(name = "um_name", nullable = false)
    private String name;

    /**
     * UI模块对应的前端 URI
     */
    @Column(name = "um_uri")
    private String uri;

}
