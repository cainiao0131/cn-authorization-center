
package org.cainiao.authorization.entity.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.BaseEntity;
import org.hibernate.annotations.Comment;
import org.nutz.lang.random.R;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 批准<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Table(name = "t_oauth2_authorization")
@Schema(name = "Authorization", description = "权限")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Authorization extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 5466389228221811896L;

    private static final int TEXT_LENGTH = 65535;

    @Column(nullable = false, length = 64, unique = true)
    private static final String ID_DESCRIPTION = "ID";
    @Comment(ID_DESCRIPTION)
    @Schema(description = ID_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    @Default
    private String id = R.UU32();

    @Column(nullable = false, length = 100)
    private static final String REGISTERED_CLIENT_ID_DESCRIPTION = "客户端ID";
    @Comment(REGISTERED_CLIENT_ID_DESCRIPTION)
    @Schema(description = REGISTERED_CLIENT_ID_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    private String registeredClientId;

    @Column(nullable = false, length = 200)
    private static final String PRINCIPAL_NAME_DESCRIPTION = "主体名称";
    @Comment(PRINCIPAL_NAME_DESCRIPTION)
    @Schema(description = PRINCIPAL_NAME_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    private String principalName;

    @Column(nullable = false, length = 100)
    private static final String AUTHORIZATION_GRANT_TYPE_DESCRIPTION = "授权类型";
    @Comment(AUTHORIZATION_GRANT_TYPE_DESCRIPTION)
    @Schema(description = AUTHORIZATION_GRANT_TYPE_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    private RegisteredClient.GrantType authorizationGrantType;

    @Column(length = 1000)
    private static final String AUTHORIZED_SCOPES_DESCRIPTION = "授权范围";
    @Comment(AUTHORIZED_SCOPES_DESCRIPTION)
    @Schema(description = AUTHORIZED_SCOPES_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String authorizedScopes;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String ATTRIBUTES_DESCRIPTION = "属性";
    @Comment(ATTRIBUTES_DESCRIPTION)
    @Schema(description = ATTRIBUTES_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String attributes;

    @Column(length = 500)
    private static final String STATE_DESCRIPTION = "state状态(客户端传入)";
    @Comment(STATE_DESCRIPTION)
    @Schema(description = STATE_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String state;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String AUTHORIZATION_CODE_VALUE_DESCRIPTION = "授权码";
    @Comment(AUTHORIZATION_CODE_VALUE_DESCRIPTION)
    @Schema(description = AUTHORIZATION_CODE_VALUE_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String authorizationCodeValue;

    @Column
    private static final String AUTHORIZATION_CODE_ISSUED_AT_DESCRIPTION = "授权码签发时间";
    @Comment(AUTHORIZATION_CODE_ISSUED_AT_DESCRIPTION)
    @Schema(description = AUTHORIZATION_CODE_ISSUED_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime authorizationCodeIssuedAt;

    @Column
    private static final String AUTHORIZATION_CODE_EXPIRES_AT_DESCRIPTION = "授权码过期时间";
    @Comment(AUTHORIZATION_CODE_EXPIRES_AT_DESCRIPTION)
    @Schema(description = AUTHORIZATION_CODE_EXPIRES_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime authorizationCodeExpiresAt;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String AUTHORIZATION_CODE_METADATA_DESCRIPTION = "授权码元数据";
    @Comment(AUTHORIZATION_CODE_METADATA_DESCRIPTION)
    @Schema(description = AUTHORIZATION_CODE_METADATA_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String authorizationCodeMetadata;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String ACCESS_TOKEN_VALUE_DESCRIPTION = "access token";
    @Comment(ACCESS_TOKEN_VALUE_DESCRIPTION)
    @Schema(description = ACCESS_TOKEN_VALUE_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String accessTokenValue;

    @Column
    private static final String ACCESS_TOKEN_ISSUED_AT_DESCRIPTION = "access token 签发时间";
    @Comment(ACCESS_TOKEN_ISSUED_AT_DESCRIPTION)
    @Schema(description = ACCESS_TOKEN_ISSUED_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime accessTokenIssuedAt;

    @Column
    private static final String ACCESS_TOKEN_EXPIRES_AT_DESCRIPTION = "access token 过期时间";
    @Comment(ACCESS_TOKEN_EXPIRES_AT_DESCRIPTION)
    @Schema(description = ACCESS_TOKEN_EXPIRES_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime accessTokenExpiresAt;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String ACCESS_TOKEN_METADATA_DESCRIPTION = "access token 元数据";
    @Comment(ACCESS_TOKEN_METADATA_DESCRIPTION)
    @Schema(description = ACCESS_TOKEN_METADATA_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String accessTokenMetadata;

    @Column(length = 100)
    private static final String ACCESS_TOKEN_TYPE_DESCRIPTION = "access token 类型";
    @Comment(ACCESS_TOKEN_TYPE_DESCRIPTION)
    @Schema(description = ACCESS_TOKEN_TYPE_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String accessTokenType;

    @Column(length = 1000)
    private static final String ACCESS_TOKEN_SCOPES_DESCRIPTION = "access token 范围";
    @Comment(ACCESS_TOKEN_SCOPES_DESCRIPTION)
    @Schema(description = ACCESS_TOKEN_SCOPES_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String accessTokenScopes;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String OIDC_ID_TOKEN_VALUE_DESCRIPTION = "oidc token";
    @Comment(OIDC_ID_TOKEN_VALUE_DESCRIPTION)
    @Schema(description = OIDC_ID_TOKEN_VALUE_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String oidcIdTokenValue;

    @Column
    private static final String OIDC_ID_TOKEN_ISSUED_AT_DESCRIPTION = "oidc token 签发时间";
    @Comment(OIDC_ID_TOKEN_ISSUED_AT_DESCRIPTION)
    @Schema(description = OIDC_ID_TOKEN_ISSUED_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime oidcIdTokenIssuedAt;

    @Column
    private static final String OIDC_ID_TOKEN_EXPIRES_AT_DESCRIPTION = "oidc token 过期时间";
    @Comment(OIDC_ID_TOKEN_EXPIRES_AT_DESCRIPTION)
    @Schema(description = OIDC_ID_TOKEN_EXPIRES_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime oidcIdTokenExpiresAt;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String OIDC_ID_TOKEN_METADATA_DESCRIPTION = "oidc token 元数据";
    @Comment(OIDC_ID_TOKEN_METADATA_DESCRIPTION)
    @Schema(description = OIDC_ID_TOKEN_METADATA_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String oidcIdTokenMetadata;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String REFRESH_TOKEN_VALUE_DESCRIPTION = "refresh token";
    @Comment(REFRESH_TOKEN_VALUE_DESCRIPTION)
    @Schema(description = REFRESH_TOKEN_VALUE_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String refreshTokenValue;

    @Column
    private static final String REFRESH_TOKEN_ISSUED_AT_DESCRIPTION = "refresh token 签发时间";
    @Comment(REFRESH_TOKEN_ISSUED_AT_DESCRIPTION)
    @Schema(description = REFRESH_TOKEN_ISSUED_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime refreshTokenIssuedAt;

    @Column
    private static final String REFRESH_TOKEN_EXPIRES_AT_DESCRIPTION = "refresh token 过期时间";
    @Comment(REFRESH_TOKEN_EXPIRES_AT_DESCRIPTION)
    @Schema(description = REFRESH_TOKEN_EXPIRES_AT_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime refreshTokenExpiresAt;

    @Column(length = TEXT_LENGTH, columnDefinition = "TEXT")
    private static final String REFRESH_TOKEN_METADATA_DESCRIPTION = "refresh token 元数据";
    @Comment(REFRESH_TOKEN_METADATA_DESCRIPTION)
    @Schema(description = REFRESH_TOKEN_METADATA_DESCRIPTION, requiredMode = RequiredMode.NOT_REQUIRED)
    private String refreshTokenMetadata;

}
