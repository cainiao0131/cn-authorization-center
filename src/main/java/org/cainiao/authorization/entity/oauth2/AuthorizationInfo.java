package org.cainiao.authorization.entity.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.nutz.dao.entity.annotation.*;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 授权记录<br />
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
@Table("t_authorization")
@Comment("授权记录")
@Schema(name = "Authorization", description = "授权记录")
public class AuthorizationInfo extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4024178385252021707L;

    @Schema(description = "客户端id", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_client_id")
    @Comment("客户端id")
    @ColDefine(width = 32, precision = 0)
    private String clientId;

    @Schema(description = "客户端类型", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_client_type")
    @Comment("客户端类型")
    @ColDefine(width = 255, precision = 0)
    private ClientType clientType;

    @Schema(description = "主体名称", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_principal_name")
    @Comment("主体名称")
    @ColDefine(width = 50, precision = 0)
    private String principalName;

    @Schema(description = "授权码", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_authorization_code_value")
    @Comment("授权码")
    @ColDefine(width = 32, precision = 0)
    private String authorizationCodeValue;

    @Schema(description = "授权时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_authorization_code_issued_at")
    @Comment("授权时间")
    @ColDefine(width = 19, precision = 0)
    @Default
    private LocalDateTime authorizationCodeIssuedAt = LocalDateTime.now();

    @Schema(description = "过期时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_authorization_code_expires_at")
    @Comment("过期时间")
    @ColDefine(width = 19, precision = 0)
    @Default
    private LocalDateTime authorizationCodeExpiresAt = LocalDateTime.now().plusMinutes(5);

    @Schema(description = "授权码元数据", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_authorization_code_metadata")
    @Comment("授权码元数据")
    @ColDefine(width = 65535, precision = 0, type = ColType.TEXT)
    private String authorizationCodeMetadata;

    @Schema(description = "授权码状态", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_authorization_code_status")
    @Comment("授权码状态")
    @ColDefine(width = 20, precision = 0)
    @Default
    private Status authorizationCodeStatus = Status.CREATED;

    @Schema(description = "Scope", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("a_scope")
    @Comment("Scope")
    @ColDefine
    @Default
    private StateExchange.Scope scope = StateExchange.Scope.BASE;

    public enum ClientType {
        WECHAT, PC
    }

    @Getter
    @AllArgsConstructor
    public enum Status {/**
         * 
         */
        CREATED("created", "已创建"),
        /**
         * 
         */
        AUTHORIZED("authorized", "已使用"),
        /**
         * 
         */
        EXPIRED("expired", "已过期");

        final String code;
        final String description;
    }
}
