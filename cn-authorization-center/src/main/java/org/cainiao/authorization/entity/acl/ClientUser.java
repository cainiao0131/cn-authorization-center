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

/**
 * 应用用户<br />
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
@Table("t_client_user")
@Comment("应用用户")
@Schema(name = "ClientUser", description = "应用用户")
public class ClientUser extends IdBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    @Column("au_client_id")
    @Comment("客户端(应用)id")
    @ColDefine(width = 32, precision = 0)
    private String clientId;

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @Column("au_user_name")
    @Comment("用户名")
    @ColDefine(width = 32, precision = 0)
    private String userName;

}
