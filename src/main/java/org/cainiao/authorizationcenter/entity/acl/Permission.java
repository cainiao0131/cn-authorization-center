package org.cainiao.authorizationcenter.entity.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_permission")
@Schema(name = "Permission", description = "权限")
public class Permission extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -6877231901538437374L;

    /**
     * 表示这个权限是为这个【系统】设计的
     */
    @TableField("per_system_id")
    @Schema(description = "系统 ID")
    private Long systemId;

    /**
     * 表示这个权限是为这个【机构】设计的
     */
    @TableField("per_organization_id")
    @Schema(description = "机构 ID")
    private Long organizationId;

    /**
     * 表示这个权限是为这个【命名空间】设计的，用于三方服务扩展
     */
    @TableField("per_auth_namespace_id")
    @Schema(description = "权限命名空间 ID")
    private Long authNamespaceId;

    @TableField("per_name")
    @Schema(description = "权限名")
    private String name;

    @TableField("per_description")
    @Schema(description = "权限描述")
    private String description;
}
