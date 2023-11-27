package org.cainiao.authorization.entity.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.BaseEntity;
import org.cainiao.common.util.constant.ICodeBook;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
@Table("t_oauth2_registered_client")
@Comment("认证客户端")
@Schema(name = "RegisteredClient", description = "认证客户端")
public class RegisteredClient extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4200298792899493644L;

    @Schema(description = "id", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("id")
    @Comment("id")
    @ColDefine(notNull = true, width = 64, precision = 0)
    @Default
    private String id = R.UU32();

    @Schema(description = " 项目Key", requiredMode = RequiredMode.REQUIRED)
    @Column("client_project_key")
    @Comment(" 项目Key")
    @ColDefine(width = 32, precision = 0)
    private String projectKey;

    @Schema(description = " 服务Id", requiredMode = RequiredMode.REQUIRED)
    @Column("client_service_id")
    @Comment(" 服务Id")
    @ColDefine(width = 100, precision = 0)
    private String serviceId;

    @Schema(description = "客户端id", requiredMode = RequiredMode.REQUIRED)
    @Column("client_id")
    @Comment("客户端id")
    @ColDefine(notNull = true, width = 100, precision = 0)
    private String clientId;

    @Schema(description = "客户端签发时间", requiredMode = RequiredMode.REQUIRED)
    @Column("client_id_issued_at")
    @Comment("客户端签发时间")
    @ColDefine(notNull = true, width = 19, precision = 0)
    private LocalDateTime clientIdIssuedAt;

    @Schema(description = "客户端秘钥", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("client_secret")
    @Comment("客户端秘钥")
    @ColDefine(width = 200, precision = 0)
    private String clientSecret;

    @Schema(description = "客户端过期时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("client_secret_expires_at")
    @Comment("客户端过期时间")
    @ColDefine(width = 19, precision = 0)
    private LocalDateTime clientSecretExpiresAt;

    @Schema(description = "客户端名称", requiredMode = RequiredMode.REQUIRED)
    @Column("client_name")
    @Comment("客户端名称")
    @ColDefine(notNull = true, width = 200, precision = 0)
    private String clientName;

    @Schema(description = "认证方法", requiredMode = RequiredMode.REQUIRED)
    @Column("client_authentication_methods")
    @Comment("认证方法")
    @ColDefine(notNull = true, width = 1000, precision = 0)
    private List<AuthenticationMethod> clientAuthenticationMethods;

    @Schema(description = "授权类型", requiredMode = RequiredMode.REQUIRED)
    @Column("authorization_grant_types")
    @Comment("授权类型")
    @ColDefine(notNull = true, width = 1000, precision = 0)
    private List<GrantType> authorizationGrantTypes;

    @Schema(description = "回调地址", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("redirect_uris")
    @Comment("回调地址")
    @ColDefine(notNull = false, width = 1000, precision = 0)
    private List<String> redirectUris;

    @Schema(description = "允许范围", requiredMode = RequiredMode.REQUIRED)
    @Column("scopes")
    @Comment("允许范围")
    @ColDefine(notNull = true, width = 1000, precision = 0)
    private List<Scope> scopes;

    @Getter
    @AllArgsConstructor
    public enum Scope implements ICodeBook {
        OPENID(OidcScopes.OPENID, Type.SCOPE, "OPENID"),
        /**
         * 
         */
        ADDRESS(OidcScopes.ADDRESS, Type.SCOPE, "地址"),
        /**
         * 
         */
        EMAIL(OidcScopes.EMAIL, Type.SCOPE, "邮箱"),
        /**
         * 
         */
        PHONE(OidcScopes.PHONE, Type.SCOPE, "手机号"),
        /**
         * 
         */
        PROFILE(OidcScopes.PROFILE, Type.SCOPE, "详情"),
        /**
         * 
         */
        PERMISSION("permission", Type.AUTHORITY, "权限");

        final String code;
        final Type type;
        final String description;

        public static Scope from(String s) {
            return Arrays.stream(values()).filter(scope -> Strings.equals(scope.getCode(), s)).findFirst().orElse(OPENID);
        }

        @Getter
        @AllArgsConstructor
        public enum Type implements ICodeBook {
            SCOPE("scope", "Oauth2 Scope"), AUTHORITY("authority", "Authority Role");

            final String code;
            final String description;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum AuthenticationMethod implements ICodeBook {
        /**
         * 
         */
        CLIENT_SECRET_BASIC("CLIENT_SECRET_BASIC", "CLIENT_SECRET_BASIC", ClientAuthenticationMethod.CLIENT_SECRET_BASIC),
        /**
         * 
         */
        CLIENT_SECRET_POST("CLIENT_SECRET_POST", "CLIENT_SECRET_POST", ClientAuthenticationMethod.CLIENT_SECRET_POST),
        /**
         * 
         */
        CLIENT_SECRET_JWT("CLIENT_SECRET_JWT", "CLIENT_SECRET_JWT", ClientAuthenticationMethod.CLIENT_SECRET_JWT),
        /**
         * 
         */
        PRIVATE_KEY_JWT("PRIVATE_KEY_JWT", "PRIVATE_KEY_JWT", ClientAuthenticationMethod.PRIVATE_KEY_JWT),
        /**
         * 
         */
        NONE("NONE", "NONE", ClientAuthenticationMethod.NONE);

        final String code;
        final String description;
        final ClientAuthenticationMethod method;

        public static AuthenticationMethod from(ClientAuthenticationMethod method) {
            return Arrays.stream(values()).filter(m -> Strings.equals(method.getValue(), m.getMethod().getValue())).findFirst().orElse(CLIENT_SECRET_BASIC);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum GrantType implements ICodeBook {
        /**
         * 
         */
        AUTHORIZATION_CODE("AUTHORIZATION_CODE", "授权码", AuthorizationGrantType.AUTHORIZATION_CODE),
        /**
         * 
         */
        CLIENT_CREDENTIALS("CLIENT_CREDENTIALS", "信任客户端", AuthorizationGrantType.CLIENT_CREDENTIALS),
        /**
         * 
         */
        JWT_BEARER("JWT_BEARER", "JWT", AuthorizationGrantType.JWT_BEARER),
        /**
         * 
         */
        PASSWORD("PASSWORD", "密码", AuthorizationGrantType.PASSWORD),
        /**
         * 
         */
        REFRESH_TOKEN("REFRESH_TOKEN", "刷新令牌", AuthorizationGrantType.REFRESH_TOKEN);

        final String code;
        final String description;
        final AuthorizationGrantType type;

        public static GrantType from(AuthorizationGrantType type) {
            return Arrays.stream(values()).filter(t -> Strings.equals(type.getValue(), t.getType().getValue())).findFirst().orElse(AUTHORIZATION_CODE);
        }
    }

    public static RegisteredClient from(org.springframework.security.oauth2.server.authorization.client.RegisteredClient client) {
        return builder()
                        .authorizationGrantTypes(client.getAuthorizationGrantTypes().stream().map(GrantType::from).collect(Collectors.toList()))
                        .clientAuthenticationMethods(client.getClientAuthenticationMethods().stream().map(AuthenticationMethod::from).collect(Collectors.toList()))
                        .clientId(client.getClientId())
                        .clientIdIssuedAt(client.getClientIdIssuedAt() == null ? LocalDateTime.now()
                                                                               : LocalDateTime.ofInstant(client.getClientIdIssuedAt(), ZoneId.systemDefault()))
                        .clientName(client.getClientName())
                        .clientSecret(client.getClientSecret())
                        .clientSecretExpiresAt(client.getClientSecretExpiresAt() == null ? LocalDateTime.now()
                                                                                         : LocalDateTime.ofInstant(client.getClientSecretExpiresAt(), ZoneId.systemDefault()))
                        .id(client.getId())
                        .redirectUris(new ArrayList<>(client.getRedirectUris()))
                        .scopes(client.getScopes().stream().map(Scope::from).collect(Collectors.toList()))
                        .build();
    }

    public org.springframework.security.oauth2.server.authorization.client.RegisteredClient toClient(ClientSettings clientSettings, TokenSettings tokenSettings) {
        org.springframework.security.oauth2.server.authorization.client.RegisteredClient.Builder builder = org.springframework.security.oauth2.server.authorization.client.RegisteredClient.withId(id)
                                                                                                                                                                                           .clientId(clientId)
                                                                                                                                                                                           .clientIdIssuedAt(clientIdIssuedAt.toInstant(ZoneOffset.UTC))
                                                                                                                                                                                           .clientName(clientName)
                                                                                                                                                                                           .clientSecret(clientSecret)
                                                                                                                                                                                           .clientSecretExpiresAt(clientSecretExpiresAt.toInstant(ZoneOffset.UTC))
                                                                                                                                                                                           .clientSettings(clientSettings)
                                                                                                                                                                                           .tokenSettings(tokenSettings);
        authorizationGrantTypes.forEach(t -> builder.authorizationGrantType(t.getType()));
        clientAuthenticationMethods.forEach(m -> builder.clientAuthenticationMethod(m.getMethod()));
        scopes.forEach(s -> builder.scope(s.getCode()));
        redirectUris.forEach(builder::redirectUri);
        return builder.build();
    }

}
