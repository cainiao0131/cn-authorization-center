package org.cainiao.authorization.entity.acl;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;

import java.io.Serial;

/**
 * 角色权限<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "t_role_permission")
@Schema(name = "RolePermission", description = "角色权限")
public class RolePermission extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 5773966477520484679L;

    @Column(name = "rp_role_key", length = 32)
    @Schema(description = "角色key", requiredMode = RequiredMode.REQUIRED)
    private String roleKey;

    @Column(name = "rp_permission_key_path", length = 200)
    @Schema(description = "权限keyPath", requiredMode = RequiredMode.REQUIRED)
    private String permissionKeyPath;

    @Column(name = "client_id", length = 32)
    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    private String clientId;

}
