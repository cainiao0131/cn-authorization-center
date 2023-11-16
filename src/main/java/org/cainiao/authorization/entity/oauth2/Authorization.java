
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
import org.cainiao.authorization.entity.Entity;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.random.R;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 批准<br />
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
@Table("t_oauth2_authorization")
@Comment("批准")
@Schema(name = "Authorization", description = "批准")
public class Authorization extends Entity {

    @Serial
    private static final long serialVersionUID = 5466389228221811896L;

    @Schema(description = "id", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("id")
    @Comment("id")
    @ColDefine(notNull = true, width = 64, precision = 0)
    @Default
    private String id = R.UU32();

    @Schema(description = "客户端id", requiredMode = RequiredMode.REQUIRED)
    @Column("registered_client_id")
    @Comment("客户端id")
    @ColDefine(notNull = true, width = 100, precision = 0)
    private String registeredClientId;

    @Schema(description = "主体名称", requiredMode = RequiredMode.REQUIRED)
    @Column("principal_name")
    @Comment("主体名称")
    @ColDefine(notNull = true, width = 200, precision = 0)
    private String principalName;

    @Schema(description = "授权类型", requiredMode = RequiredMode.REQUIRED)
    @Column("authorization_grant_type")
    @Comment("授权类型")
    @ColDefine(notNull = true, width = 100, precision = 0)
    private RegisteredClient.GrantType authorizationGrantType;

    @Schema(description = "授权范围", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("authorized_scopes")
    @Comment("授权范围")
    @ColDefine(notNull = false, width = 1000, precision = 0)
    private String authorizedScopes;

    @Schema(description = "属性", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("attributes")
    @Comment("属性")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String attributes;

    @Schema(description = "state状态(客户端传入)", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("state")
    @Comment("state状态(客户端传入)")
    @ColDefine(notNull = false, width = 500, precision = 0)
    private String state;

    @Schema(description = "授权码", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("authorization_code_value")
    @Comment("授权码")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String authorizationCodeValue;

    @Schema(description = "授权码签发时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("authorization_code_issued_at")
    @Comment("授权码签发时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime authorizationCodeIssuedAt;

    @Schema(description = "授权码过期时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("authorization_code_expires_at")
    @Comment("授权码过期时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime authorizationCodeExpiresAt;

    @Schema(description = "授权码元数据", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("authorization_code_metadata")
    @Comment("授权码元数据")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String authorizationCodeMetadata;

    @Schema(description = "access token", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("access_token_value")
    @Comment("access token")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String accessTokenValue;

    @Schema(description = "access token 签发时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("access_token_issued_at")
    @Comment("access token 签发时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime accessTokenIssuedAt;

    @Schema(description = "access token 过期时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("access_token_expires_at")
    @Comment("access token 过期时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime accessTokenExpiresAt;

    @Schema(description = "access token 元数据", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("access_token_metadata")
    @Comment("access token 元数据")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String accessTokenMetadata;

    @Schema(description = "access token 类型", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("access_token_type")
    @Comment("access token 类型")
    @ColDefine(notNull = false, width = 100, precision = 0)
    private String accessTokenType;

    @Schema(description = "access token 范围", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("access_token_scopes")
    @Comment("access token 范围")
    @ColDefine(notNull = false, width = 1000, precision = 0)
    private String accessTokenScopes;

    @Schema(description = "oidc token", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("oidc_id_token_value")
    @Comment("oidc token")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String oidcIdTokenValue;

    @Schema(description = "oidc token 签发时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("oidc_id_token_issued_at")
    @Comment("oidc token 签发时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime oidcIdTokenIssuedAt;

    @Schema(description = "oidc token 过期时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("oidc_id_token_expires_at")
    @Comment("oidc token 过期时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime oidcIdTokenExpiresAt;

    @Schema(description = "oidc token 元数据", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("oidc_id_token_metadata")
    @Comment("oidc token 元数据")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String oidcIdTokenMetadata;

    @Schema(description = "refresh token", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("refresh_token_value")
    @Comment("refresh token")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String refreshTokenValue;

    @Schema(description = "refresh token 签发时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("refresh_token_issued_at")
    @Comment("refresh token 签发时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime refreshTokenIssuedAt;

    @Schema(description = "refresh token 过期时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("refresh_token_expires_at")
    @Comment("refresh token 过期时间")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private LocalDateTime refreshTokenExpiresAt;

    @Schema(description = "refresh token 元数据", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("refresh_token_metadata")
    @Comment("refresh token 元数据")
    @ColDefine(type = ColType.TEXT, notNull = false, width = 65535, precision = 0)
    private String refreshTokenMetadata;

}
