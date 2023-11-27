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
@Table("t_user_role")
@Comment("用户角色")
@Schema(name = "UserRole", description = "用户角色")
public class UserRole extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -2215125869749312343L;

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @Column("ur_user_name")
    @Comment("用户名")
    @ColDefine(width = 50, precision = 0)
    private String userName;

    @Schema(description = "角色key", requiredMode = RequiredMode.REQUIRED)
    @Column("ur_role_key")
    @Comment("角色key")
    @ColDefine(width = 32, precision = 0)
    private String roleKey;

    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    @Column("client_id")
    @Comment("客户端(应用)id")
    @ColDefine(width = 32, precision = 0)
    private String clientId;

}
