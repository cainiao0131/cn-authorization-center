package org.cainiao.authorization.entity.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.cainiao.common.utils.enums.ICodeBook;
import org.nutz.dao.entity.annotation.*;

import java.io.Serial;

/**
 * 用户账号绑定<br />
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
@Table("t_user_bind")
@Comment("用户账号绑定")
@Schema(name = "UserBind", description = "用户账号绑定")
public class UserBind extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4290496391404795358L;

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @Column("b_user_name")
    @Comment("用户名")
    @ColDefine(width = 50, precision = 0)
    private String userName;

    @Schema(description = "OAuth2 渠道类型", requiredMode = RequiredMode.REQUIRED)
    @Column("b_type")
    @Comment("OAuth2 渠道类型")
    @ColDefine(width = 20, precision = 0)
    private Type type;

    @Schema(description = "openid", requiredMode = RequiredMode.REQUIRED)
    @Column("b_openid")
    @Comment("openid")
    @ColDefine(width = 128, precision = 0)
    private String openid;

    @Schema(description = "metadata", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("b_metadata")
    @Comment("metadata")
    @ColDefine(type = ColType.TEXT)
    private String metadata;

    @Schema(description = "uuid", requiredMode = RequiredMode.REQUIRED)
    @Column("b_uuid")
    @Comment("uuid")
    @ColDefine(width = 128, precision = 0)
    private String uuid;

    @Getter
    @AllArgsConstructor
    public enum Type implements ICodeBook {
        WORK_WECHAT("WORK_WECHAT", "企业微信"),
        /**
         * 
         */
        AD("AD", "AD域账号"),
        /**
         * 
         */
        GITEA("gitea", "GITEA");

        final String code;
        final String description;
    }

}
