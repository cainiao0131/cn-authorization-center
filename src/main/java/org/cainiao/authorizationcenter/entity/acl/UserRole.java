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
@TableName("t_user_role")
@Schema(name = "UserRole", description = "用户角色")
public class UserRole extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 5196719037609214453L;

    /**
     * 表示用户登录这个【系统】时才授予这个角色
     */
    @TableField("per_system_id")
    @Schema(description = "系统 ID")
    private Long systemId;

    /**
     * 表示用户以这个【机构】登录时才授予这个角色
     */
    @TableField("per_organization_id")
    @Schema(description = "机构 ID")
    private Long organizationId;

    /**
     * 表示用户进入这个【命名空间】的上下文时才授予这个角色，用于三方服务扩展
     */
    @TableField("per_auth_namespace_id")
    @Schema(description = "权限命名空间 ID")
    private Long authNamespaceId;

    @TableField("ur_user_id")
    @Schema(description = "用户 ID")
    private long userId;

    @TableField("ur_role_id")
    @Schema(description = "角色 ID")
    private long roleId;
}
