package org.cainiao.authorizationcenter.entity.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.dao.IdBaseEntity;

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
@TableName("t_role")
@Schema(name = "Role", description = "角色")
public class Role extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -6371767042656712416L;

    /**
     * 表示这个角色是为这个【系统】设计的
     */
    @TableField("rol_system_id")
    @Schema(description = "系统 ID")
    private Long systemId;

    /**
     * 表示这个角色是为这个【机构】设计的
     */
    @TableField("rol_organization_id")
    @Schema(description = "机构 ID")
    private Long organizationId;

    /**
     * 表示这个角色是为这个【命名空间】设计的，用于三方服务扩展
     */
    @TableField("rol_auth_namespace_id")
    @Schema(description = "权限命名空间 ID")
    private Long authNamespaceId;

    @TableField("rol_name")
    @Schema(description = "角色名")
    private String name;

    @TableField("rol_description")
    @Schema(description = "角色描述")
    private String description;
}
