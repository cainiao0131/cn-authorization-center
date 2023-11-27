package org.cainiao.authorization.entity.acl;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

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
@Table("t_role")
@Comment("角色")
@Schema(name = "Role", description = "角色")
public class Role extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -5626336016780216180L;

    @Schema(description = "角色key,英文,用来做业务", requiredMode = RequiredMode.REQUIRED)
    @Column("r_key")
    @Comment("角色key,英文,用来做业务")
    @ColDefine(width = 32, precision = 0)
    private String key;

    @Schema(description = "角色名称,中文用来做标识", requiredMode = RequiredMode.REQUIRED)
    @Column("r_name")
    @Comment("角色名称,中文用来做标识")
    @ColDefine(width = 50, precision = 0)
    private String name;

    @Schema(description = "角色描述", requiredMode = RequiredMode.AUTO)
    @Column("r_description")
    @Comment("角色描述")
    @ColDefine(width = 128, precision = 0)
    private String description;

    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    @Column("client_id")
    @Comment("客户端(应用)id")
    @ColDefine(width = 32, precision = 0)
    private String clientId;

}
