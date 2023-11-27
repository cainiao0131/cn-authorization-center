package org.cainiao.authorization.entity.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.nutz.dao.entity.annotation.*;

import java.io.Serial;

/**
 * state信息转换<br />
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
@Table("t_state_exchange")
@Comment("state信息转换")
@Schema(name = "StateExchange", description = "state信息转换")
public class StateExchange extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -874882852381363167L;

    @Schema(description = "客户端id", requiredMode = RequiredMode.REQUIRED)
    @Column("e_client_id")
    @Comment("客户端id")
    @ColDefine(notNull = true, width = 32, precision = 0)
    private String clientId;

    @Schema(description = "唯一标识", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("e_uuid")
    @Comment("唯一标识")
    @ColDefine(notNull = true, width = 32, precision = 0)
    private String uuid;

    @Schema(description = "原始state信息", requiredMode = RequiredMode.REQUIRED)
    @Column("e_state")
    @Comment("原始state信息")
    @ColDefine(width = 32, precision = 0)
    private String state;

    @Schema(description = "回调URL", requiredMode = RequiredMode.REQUIRED)
    @Column("e_callback_url")
    @Comment("回调URL")
    @ColDefine(width = 500, precision = 0)
    private String callbackUrl;

    @Schema(description = "是否重定向", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("e_redirect")
    @Comment("是否重定向")
    @ColDefine
    private boolean redirect;

    @Schema(description = "Scope", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("e_scope")
    @Comment("Scope")
    @ColDefine
    @Default
    private Scope scope = Scope.BASE;

    public enum Scope {
        BASE, DETAIL
    }

}
