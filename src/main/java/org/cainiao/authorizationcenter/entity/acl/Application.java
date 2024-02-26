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
    @Column(name = "a_system_id", nullable = false)
    private String systemId;

    /**
     * 应用名称
     */
    @Column(name = "a_name", nullable = false, unique = true)
    private String name;

    /**
     * 应用对应的服务名<br />
     * 对于前端应用，是前端应用的外部访问域名<br />
     * 对于后端应用，有这些可能性：注册中心服务名、服务网格虚拟服务名、k8s服务名、IP端口、域名
     */
    @Column(name = "a_service_name")
    private String serviceName;

    /**
     * 【应用】对应的URI<br />
     * 用于不同【应用】以URI来隔离的场景
     */
    @Column(name = "a_service_uri")
    private String serviceUri;
}
