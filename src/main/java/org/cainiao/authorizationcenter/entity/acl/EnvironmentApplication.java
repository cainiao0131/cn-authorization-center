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
 * 很多领域对象与环境相关，例如【前端 UI 模块】，应该是环境独立的<br />
 * 即不同的环境应该有独属于自己的【UI 模块】数据，因为不同的环境开发周期不同，例如测试环境调整后的【UI 模块】不一定第一时间同步到生产环境<br />
 * 因此设计了一个【环境】表和一个【环境】与【应用】的多对多关系表<br />
 * 从现实的角度看，所有与【环境】相关的领域对象，都应与【应用】关联，因为实际部署到各个环境的是【应用】<br />
 * 例如【UI 模块】一定归属于某个【前端应用】<br />
 * 基于此，属于某【应用】的领域对象，例如【UI 模块】上的外键应该引用【环境-应用】的主键，而不是【应用】的主键<br />
 * 因为一个实际运行的【应用】一定是运行在一个环境中的，因此【环境-应用】表的条目才对应一个实际运行的【应用】<br />
 * 而【应用】表的条目，代表的是一个【抽象应用】的概念，代表的是一个【开发目标】<br />
 * 抽象与具体两个概念都很重要，缺一不可，否则就会造成后续设计困难<br />
 * 与【具体应用】
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_environment_application")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class EnvironmentApplication extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 1067611570198834293L;

    /**
     * 环境ID
     */
    @Column(name = "envapp_environment_id", nullable = false)
    private long environmentId;

    /**
     * 应用ID
     */
    @Column(name = "envapp_application_id", nullable = false)
    private long applicationId;

    /**
     * 应用对应的服务名<br />
     * 对于前端应用，是前端应用的外部访问域名<br />
     * 对于后端应用，有这些可能性：注册中心服务名、服务网格虚拟服务名、k8s服务名、IP端口、域名
     */
    @Column(name = "envapp_service_name")
    private String serviceName;

    /**
     * 【应用】对应的URI<br />
     * 用于不同【应用】以URI来隔离的场景
     */
    @Column(name = "envapp_service_uri")
    private String serviceUri;
}
