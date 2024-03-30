package org.cainiao.authorizationcenter.entity.acl;

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
    @Column(name = "uim_parent_id")
    private Long parentId;

    /**
     * 对应前端路由中的 name
     */
    @Column(name = "uim_key", nullable = false)
    private String key;

    /**
     * 环境应用 ID
     */
    @Column(name = "uim_environment_application_id", nullable = false)
    private long environmentApplicationId;

    /**
     * UI模块名称
     */
    @Column(name = "uim_name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "uim_description")
    private String description;

    /**
     * UI模块对应的前端 URI
     */
    @Column(name = "uim_uri")
    private String uri;
}
