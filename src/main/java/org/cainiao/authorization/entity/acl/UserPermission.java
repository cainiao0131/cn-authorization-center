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
 * 用户权限<br />
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
@Table("t_user_permission")
@Comment("用户权限")
@Schema(name = "UserPermission", description = "用户权限")
public class UserPermission extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -2404207386241341247L;

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @Column("up_user_name")
    @Comment("用户名")
    @ColDefine(width = 50, precision = 0)
    private String userName;

    @Schema(description = "权限keyPath", requiredMode = RequiredMode.REQUIRED)
    @Column("up_permission_key_path")
    @Comment("权限keyPath")
    @ColDefine(width = 200, precision = 0)
    private String permissionKeyPath;

    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    @Column("client_id")
    @Comment("客户端(应用)id")
    @ColDefine(width = 32, precision = 0)
    private String clientId;

}
