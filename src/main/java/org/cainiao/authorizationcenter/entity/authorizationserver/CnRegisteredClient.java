package org.cainiao.authorizationcenter.entity.authorizationserver;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.AuthorizationGrantTypeSetTypeHandler;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.ClientAuthenticationMethodSetTypeHandler;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.ClientSettingsTypeHandler;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.TokenSettingsTypeHandler;
import org.cainiao.common.dao.IdBaseEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.Serial;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 对应 RegisteredClient，表示注册到授权服务器的 OAuth2 客户端<br />
 * JpaRegisteredClient 没有直接继承 RegisteredClient<br />
 * 因为 RegisteredClient 被设计为不可变的类，没有 setter 方法<br />
 * 而用于存储层的实体 JpaRegisteredClient 还是很有必要带有 setter 方法的<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oauth2_registered_client")
@Schema(name = "CnRegisteredClient", description = "注册的客户端")
public class CnRegisteredClient extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -1465601057374081364L;

    @TableField("orc_tenant_id")
    @Schema(description = "租户 ID")
    private long tenantId;

    /**
     * RegisteredClient 的 ID，与数据库 ID 主键重名了，用这个字段保存
     */
    private String registeredClientId;

    /**
     * 主键，表内唯一
     */
    private String clientId;

    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    private String clientName;

    @TableField(typeHandler = ClientAuthenticationMethodSetTypeHandler.class)
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @TableField(typeHandler = AuthorizationGrantTypeSetTypeHandler.class)
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    private Set<String> redirectUris;

    private Set<String> postLogoutRedirectUris;

    private Set<String> scopes;

    @TableField(typeHandler = ClientSettingsTypeHandler.class)
    private ClientSettings clientSettings;

    @TableField(typeHandler = TokenSettingsTypeHandler.class)
    private TokenSettings tokenSettings;

    public static CnRegisteredClient from(RegisteredClient registeredClient) {
        return CnRegisteredClient.builder()
            .registeredClientId(registeredClient.getId())
            .clientId(registeredClient.getClientId())
            .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
            .clientSecret(registeredClient.getClientSecret())
            .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
            .clientName(registeredClient.getClientName())
            .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods())
            .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes())
            .redirectUris(registeredClient.getRedirectUris())
            .postLogoutRedirectUris(registeredClient.getPostLogoutRedirectUris())
            .scopes(registeredClient.getScopes())
            .clientSettings(registeredClient.getClientSettings())
            .tokenSettings(registeredClient.getTokenSettings())
            .build();
    }

    public RegisteredClient toRegisteredClient() {
        RegisteredClient.Builder builder = RegisteredClient.withId(getRegisteredClientId());
        builder.clientId(getClientId());
        builder.clientIdIssuedAt(getClientIdIssuedAt());
        builder.clientSecret(getClientSecret());
        builder.clientSecretExpiresAt(getClientSecretExpiresAt());
        builder.clientName(getClientName());
        builder.clientAuthenticationMethods(methods -> methods.addAll(getClientAuthenticationMethods()));
        builder.authorizationGrantTypes(grantTypes -> grantTypes.addAll(getAuthorizationGrantTypes()));
        builder.redirectUris(uris -> uris.addAll(getRedirectUris()));
        builder.postLogoutRedirectUris(redirectUris -> redirectUris.addAll(getPostLogoutRedirectUris()));
        builder.scopes(scopes -> scopes.addAll(getScopes()));
        builder.clientSettings(getClientSettings());
        builder.tokenSettings(getTokenSettings());
        return builder.build();
    }

    public CnRegisteredClient(LocalDateTime createdTime, LocalDateTime updatedTime, String createdBy, String updatedBy,
                              String clientId, Instant clientIdIssuedAt, String clientSecret,
                              Instant clientSecretExpiresAt, String clientName,
                              Set<ClientAuthenticationMethod> clientAuthenticationMethods,
                              Set<AuthorizationGrantType> authorizationGrantTypes,
                              Set<String> redirectUris, Set<String> postLogoutRedirectUris, Set<String> scopes,
                              ClientSettings clientSettings, TokenSettings tokenSettings) {
        super(createdBy, createdTime, updatedBy, updatedTime);
        this.clientId = clientId;
        this.clientIdIssuedAt = clientIdIssuedAt;
        this.clientSecret = clientSecret;
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        this.clientName = clientName;
        this.clientAuthenticationMethods = clientAuthenticationMethods;
        this.authorizationGrantTypes = authorizationGrantTypes;
        this.redirectUris = redirectUris;
        this.postLogoutRedirectUris = postLogoutRedirectUris;
        this.scopes = scopes;
        this.clientSettings = clientSettings;
        this.tokenSettings = tokenSettings;
    }
}
