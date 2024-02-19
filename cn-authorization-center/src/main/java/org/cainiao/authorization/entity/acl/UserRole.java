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
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * 用户角色<br />
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
@Table(name = "t_user_role")
@Schema(name = "UserRole", description = "用户角色")
public class UserRole extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -2215125869749312343L;

    @Column(name = "ur_user_name", length = 50)
    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    private String userName;

    @Column(name = "ur_role_key", length = 32)
    @Schema(description = "角色key", requiredMode = RequiredMode.REQUIRED)
    private String roleKey;

    @Column(name = "client_id", length = 32)
    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    private String clientId;

}
