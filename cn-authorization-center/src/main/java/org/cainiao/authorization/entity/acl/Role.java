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
 * 角色<br />
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
@Table(name = "t_role")
@Schema(name = "Role", description = "角色")
public class Role extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -5626336016780216180L;

    @Column(name = "r_key", length = 32)
    @Schema(description = "角色key,英文,用来做业务", requiredMode = RequiredMode.REQUIRED)
    private String key;

    @Column(name = "r_name", length = 50)
    @Schema(description = "角色名称,中文用来做标识", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Column(name = "r_description", length = 128)
    @Schema(description = "角色描述", requiredMode = RequiredMode.AUTO)
    private String description;

    @Column(name = "client_id", length = 32)
    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    private String clientId;

}
