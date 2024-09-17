package org.cainiao.authorizationcenter.entity.oauth2login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.SetTypeHandler;
import org.cainiao.common.dao.IdBaseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import java.io.Serial;
import java.time.Instant;
import java.util.Set;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_oauth2_authorized_client")
@Schema(name = "CnOAuth2AuthorizedClient", description = "已授权的 OAuth2 客户端")
public class CnOAuth2AuthorizedClient extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 6596171918305449732L;

    @TableField("oac_registration_id")
    @Schema(description = "registration id")
    private String registrationId;

    @TableField("oac_principal_name")
    @Schema(description = "主体名称")
    private String principalName;

    @TableField("oac_access_token")
    @Schema(description = "access token")
    private String accessToken;

    @TableField("oac_access_token_issued_at")
    @Schema(description = "access token issued at")
    private Instant accessTokenIssuedAt;

    @TableField("oac_access_token_expires_at")
    @Schema(description = "access token expires at")
    private Instant accessTokenExpiresAt;

    @TableField(value = "oac_access_token_scopes", typeHandler = SetTypeHandler.class)
    @Schema(description = "access token scopes")
    private Set<String> accessTokenScopes;

    @TableField("oac_refresh_token")
    @Schema(description = "refresh token")
    private String refreshToken;

    @TableField("oac_refresh_token_issued_at")
    @Schema(description = "refresh token issued at")
    private Instant refreshTokenIssuedAt;

    @TableField("oac_refresh_token_expires_at")
    @Schema(description = "refresh token expires at")
    private Instant refreshTokenExpiresAt;

    public static CnOAuth2AuthorizedClient from(OAuth2AuthorizedClient oAuth2AuthorizedClient, String principalName) {
        ClientRegistration clientRegistration = oAuth2AuthorizedClient.getClientRegistration();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
        CnOAuth2AuthorizedClientBuilder<?, ?> cnOAuth2AuthorizedClientBuilder = CnOAuth2AuthorizedClient.builder()
            .registrationId(clientRegistration.getRegistrationId())
            .principalName(principalName)
            .accessToken(accessToken.getTokenValue())
            .accessTokenIssuedAt(accessToken.getIssuedAt())
            .accessTokenExpiresAt(accessToken.getExpiresAt())
            .accessTokenScopes(accessToken.getScopes());
        OAuth2RefreshToken oAuth2RefreshToken = oAuth2AuthorizedClient.getRefreshToken();
        if (oAuth2RefreshToken != null) {
            cnOAuth2AuthorizedClientBuilder.refreshToken(oAuth2RefreshToken.getTokenValue())
                .refreshTokenIssuedAt(oAuth2RefreshToken.getIssuedAt())
                .refreshTokenExpiresAt(oAuth2RefreshToken.getExpiresAt());
        }
        return cnOAuth2AuthorizedClientBuilder.build();
    }

    public OAuth2AuthorizedClient toOAuth2AuthorizedClient(ClientRegistration clientRegistration) {
        return new OAuth2AuthorizedClient(clientRegistration,
            getPrincipalName(),
            new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, getAccessToken(),
                getAccessTokenIssuedAt(), getAccessTokenExpiresAt(), getAccessTokenScopes()),
            new OAuth2RefreshToken(getRefreshToken(), getRefreshTokenIssuedAt(), getRefreshTokenExpiresAt()));
    }
}
