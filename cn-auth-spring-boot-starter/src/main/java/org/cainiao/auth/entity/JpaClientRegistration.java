package org.cainiao.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.auth.dao.converter.JsonStringAuthenticationMethodConverter;
import org.cainiao.auth.dao.converter.JsonStringAuthorizationGrantTypeConverter;
import org.cainiao.auth.dao.converter.JsonStringClientAuthenticationMethodConverter;
import org.cainiao.common.dao.converter.JsonStringMapConverter;
import org.cainiao.common.dao.converter.JsonStringSetConverter;
import org.cainiao.common.entity.IdBaseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "oauth2_client_registration")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class JpaClientRegistration extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 7194103823301276843L;

    @Column(nullable = false)
    private String registrationId;

    @Column(nullable = false)
    private String clientId;

    private String clientSecret;

    @Convert(converter = JsonStringAuthorizationGrantTypeConverter.class)
    private AuthorizationGrantType authorizationGrantType;

    @Column(nullable = false)
    private String clientName;

    private String redirectUri;

    @Convert(converter = JsonStringSetConverter.class)
    private Set<String> scopes;

    @Convert(converter = JsonStringClientAuthenticationMethodConverter.class)
    private ClientAuthenticationMethod clientAuthenticationMethod;

    @Embedded
    private JpaProviderDetails providerDetails;

    /**
     * 是否从供应商 OIDC 配置端点拉取 OAuth2 注册信息
     */
    private boolean fromIssuerLocation;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class JpaProviderDetails implements Serializable {

        @Serial
        private static final long serialVersionUID = -1318434447964086126L;

        private String authorizationUri;

        private String tokenUri;

        @Embedded
        private JpaUserInfoEndpoint userInfoEndpoint;

        private String jwkSetUri;

        private String issuerUri;

        @Convert(converter = JsonStringMapConverter.class)
        private Map<String, Object> configurationMetadata;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class JpaUserInfoEndpoint implements Serializable {

        @Serial
        private static final long serialVersionUID = 6844667220249523331L;

        @Column(name = "user_info_uri")
        private String uri;

        @Column(name = "user_info_authentication_method")
        @Convert(converter = JsonStringAuthenticationMethodConverter.class)
        private AuthenticationMethod authenticationMethod;

        private String userNameAttributeName;
    }

    public ClientRegistration toRegisteredClient() {
        JpaProviderDetails jpaProviderDetails = getProviderDetails();
        JpaUserInfoEndpoint jpaUserInfoEndpoint = jpaProviderDetails.getUserInfoEndpoint();
        String registrationId_ = getRegistrationId();

        ClientRegistration.Builder builder = isFromIssuerLocation()
            /*
             * 如果配置 fromIssuerLocation("http://127.0.0.1:9000/issuer") 则会依次查询：
             * http://127.0.0.1:9000/issuer/.well-known/openid-configuration
             * http://127.0.0.1:9000/.well-known/openid-configuration/issuer
             * http://127.0.0.1:9000/.well-known/oauth-authorization-server/issuer
             * 在第一个返回 200 响应时停止
             */
            ? ClientRegistrations.fromIssuerLocation(jpaProviderDetails.getIssuerUri()).registrationId(registrationId_)
            : ClientRegistration.withRegistrationId(registrationId_)
            .authorizationGrantType(getAuthorizationGrantType())
            .redirectUri(getRedirectUri())
            .clientAuthenticationMethod(getClientAuthenticationMethod())
            .authorizationUri(jpaProviderDetails.getAuthorizationUri())
            .tokenUri(jpaProviderDetails.getTokenUri())
            .userInfoUri(jpaUserInfoEndpoint.getUri())
            .userInfoAuthenticationMethod(jpaUserInfoEndpoint.getAuthenticationMethod())
            .userNameAttributeName(jpaUserInfoEndpoint.getUserNameAttributeName())
            .jwkSetUri(jpaProviderDetails.getJwkSetUri())
            .issuerUri(jpaProviderDetails.getIssuerUri())
            .providerConfigurationMetadata(jpaProviderDetails.getConfigurationMetadata());
        return builder
            .clientName(getClientName())
            .clientId(getClientId())
            .scope(getScopes())
            .clientSecret(getClientSecret()).build();
    }
}
