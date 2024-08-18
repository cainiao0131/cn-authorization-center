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
@TableName("t_role_permission")
@Schema(name = "RolePermission", description = "角色权限")
public class RolePermission extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 5196719037609214453L;

    @TableField("rp_role_id")
    @Schema(description = "角色 ID")
    private long roleId;

    @TableField("rp_permission_id")
    @Schema(description = "权限 ID")
    private long permissionId;
}
