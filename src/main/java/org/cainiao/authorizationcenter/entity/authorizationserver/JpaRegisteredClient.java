package org.cainiao.authorizationcenter.entity.authorizationserver;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorizationcenter.entity.authorizationserver.converter.JsonStringAuthorizationGrantTypeSetConverter;
import org.cainiao.authorizationcenter.entity.authorizationserver.converter.JsonStringClientAuthenticationMethodSetConverter;
import org.cainiao.authorizationcenter.entity.authorizationserver.converter.JsonStringClientSettingsConverter;
import org.cainiao.authorizationcenter.entity.authorizationserver.converter.JsonStringTokenSettingsConverter;
import org.cainiao.common.dao.converter.JsonStringSetConverter;
import org.cainiao.common.entity.IdBaseEntity;
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
 * JpaRegisteredClient 没有直接继承 RegisteredClient<br />
 * 因为 RegisteredClient 被设计为不可变的类，没有 setter 方法<br />
 * 而用于存储层的实体 JpaRegisteredClient 还是很有必要带有 setter 方法的<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "oauth2_registered_client")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class JpaRegisteredClient extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -1465601057374081364L;

    /**
     * RegisteredClient 的 ID，与数据库 ID 主键重名了，用这个字段保存
     */
    @Column(nullable = false)
    private String registeredClientId;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private Instant clientIdIssuedAt;

    private String clientSecret;

    private Instant clientSecretExpiresAt;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    @Convert(converter = JsonStringClientAuthenticationMethodSetConverter.class)
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @Column(nullable = false)
    @Convert(converter = JsonStringAuthorizationGrantTypeSetConverter.class)
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    @Convert(converter = JsonStringSetConverter.class)
    private Set<String> redirectUris;

    @Convert(converter = JsonStringSetConverter.class)
    private Set<String> postLogoutRedirectUris;

    @Convert(converter = JsonStringSetConverter.class)
    private Set<String> scopes;

    @Convert(converter = JsonStringClientSettingsConverter.class)
    private ClientSettings clientSettings;

    @Convert(converter = JsonStringTokenSettingsConverter.class)
    private TokenSettings tokenSettings;

    public static JpaRegisteredClient from(RegisteredClient registeredClient) {
        return JpaRegisteredClient.builder()
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

    public JpaRegisteredClient(LocalDateTime createdTime, LocalDateTime updatedTime, String createdBy, String updatedBy,
                               boolean deleted, String clientId, Instant clientIdIssuedAt, String clientSecret,
                               Instant clientSecretExpiresAt, String clientName,
                               Set<ClientAuthenticationMethod> clientAuthenticationMethods,
                               Set<AuthorizationGrantType> authorizationGrantTypes,
                               Set<String> redirectUris, Set<String> postLogoutRedirectUris, Set<String> scopes,
                               ClientSettings clientSettings, TokenSettings tokenSettings) {
        super(createdTime, updatedTime, createdBy, updatedBy, deleted);
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
