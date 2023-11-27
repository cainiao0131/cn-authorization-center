package org.cainiao.authorization.entity.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.BaseEntity;
import org.cainiao.common.util.constant.Codebook;
import org.cainiao.common.util.constant.ICodeBook;
import org.nutz.dao.entity.annotation.*;
import org.nutz.json.JsonField;
import org.nutz.lang.Strings;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.Serial;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

/**
 * 认证客户端Token设置<br />
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
@Table("t_oauth2_registered_client_token_setting")
@Comment("认证客户端Token设置")
@Schema(name = "RegisteredClientTokenSetting", description = "认证客户端Token设置")
public class RegisteredClientTokenSetting extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1572591315696438917L;

    @Schema(description = "客户端id", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("client_id")
    @Comment("客户端id")
    @ColDefine(notNull = true, width = 100, precision = 0)
    private String clientId;

    @Schema(description = "Token格式", requiredMode = RequiredMode.AUTO)
    @Column("access_token_format")
    @Comment("Token格式")
    @ColDefine(width = 50, precision = 0)
    @Default
    private OAuth2TokenFormat accessTokenFormat = OAuth2TokenFormat.SELF_CONTAINED;

    @Schema(description = "accessToken有效时间(单位:分钟)", requiredMode = RequiredMode.AUTO)
    @Column("access_token_time_to_live")
    @Comment("accessToken有效时间(单位:分钟)")
    @Default
    private long accessTokenTimeToLive = 5;

    @Schema(description = "授权码有效时间(单位:分钟)", requiredMode = RequiredMode.AUTO)
    @Column("authorization_code_time_to_live")
    @Comment("授权码有效时间(单位:分钟)")
    @Default
    private long authorizationCodeTimeToLive = 5;

    @Schema(description = "刷新token有效时间(单位:分钟)", requiredMode = RequiredMode.AUTO)
    @Column("refresh_token_time_to_live")
    @Comment("刷新token有效时间(单位:分钟)")
    @Default
    private long refreshTokenTimeToLive = 60;

    @Schema(description = "Token签名算法", requiredMode = RequiredMode.AUTO)
    @Column("id_token_signature_algorithm")
    @Comment("Token签名算法")
    @ColDefine(width = 20, precision = 0)
    @Default
    private Signature idTokenSignatureAlgorithm = Signature.RS256;

    @Schema(description = "是否重用刷新token", requiredMode = RequiredMode.AUTO)
    @Column("reuse_refresh_tokens")
    @Comment("是否重用刷新token")
    @ColDefine(width = 1, precision = 0)
    @Default
    private boolean reuseRefreshTokens = true;

    @JsonField
    public Codebook getAccessTokenFormatInfo() {
        return Optional.of(getAccessTokenFormat()).orElse(OAuth2TokenFormat.SELF_CONTAINED).build();
    }

    public void setAccessTokenFormatInfo(Codebook sexInfo) {
        setAccessTokenFormat(OAuth2TokenFormat.valueOf(sexInfo.getName()));
    }

    @JsonField
    public Codebook getIdTokenSignatureAlgorithmInfo() {
        return Optional.of(getIdTokenSignatureAlgorithm()).orElse(Signature.RS256).build();
    }

    public void setIdTokenSignatureAlgorithmInfo(Codebook sexInfo) {
        setIdTokenSignatureAlgorithm(Signature.valueOf(sexInfo.getName()));
    }

    @Getter
    @AllArgsConstructor
    public enum Signature implements ICodeBook {
        /**
         * 
         */
        RS256("RS256", SignatureAlgorithm.RS256),
        /**
         * 
         */
        RS384("RS384", SignatureAlgorithm.RS384),
        /**
         * 
         */
        RS512("RS512", SignatureAlgorithm.RS512),
        /**
         * 
         */
        ES256("ES256", SignatureAlgorithm.ES256),
        /**
         * 
         */
        ES384("ES384", SignatureAlgorithm.ES384),
        /**
         * 
         */
        ES512("ES512", SignatureAlgorithm.ES512),
        /**
         * 
         */
        PS256("PS256", SignatureAlgorithm.PS256),
        /**
         * 
         */
        PS384("PS384", SignatureAlgorithm.PS384),
        /**
         * 
         */
        PS512("PS512", SignatureAlgorithm.PS512);

        final String code;

        final SignatureAlgorithm algorithm;

        @Override
        public String getDescription() {
            return code;
        }

        public static Signature from(SignatureAlgorithm signatureAlgorithm) {
            return Arrays.stream(values()).filter(a -> Strings.equals(signatureAlgorithm.getName(), a.getAlgorithm().getName())).findFirst().orElse(RS512);
        }

    }

    @Getter
    @AllArgsConstructor
    public enum OAuth2TokenFormat implements ICodeBook {
        /**
         * 
         */
        SELF_CONTAINED("SELF_CONTAINED", "自包含", org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.SELF_CONTAINED),
        /**
         * 
         */
        REFERENCE("REFERENCE", "引用", org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.REFERENCE);

        final String code;
        final String description;
        final org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat format;

        public static OAuth2TokenFormat from(org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat format) {
            return Arrays.stream(values()).filter(f -> Strings.equals(format.getValue(), f.getFormat().getValue())).findFirst().orElse(SELF_CONTAINED);
        }

    }

    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                            .accessTokenFormat(accessTokenFormat.getFormat())
                            .accessTokenTimeToLive(Duration.ofMinutes(accessTokenTimeToLive))
                            .authorizationCodeTimeToLive(Duration.ofMinutes(authorizationCodeTimeToLive))
                            .refreshTokenTimeToLive(Duration.ofMinutes(refreshTokenTimeToLive))
                            .idTokenSignatureAlgorithm(idTokenSignatureAlgorithm.getAlgorithm())
                            .reuseRefreshTokens(reuseRefreshTokens)
                            .build();
    }

    public static RegisteredClientTokenSetting from(TokenSettings tokenSettings, String clientId) {
        return builder()
                        .clientId(clientId)
                        .accessTokenFormat(OAuth2TokenFormat.from(tokenSettings.getAccessTokenFormat()))
                        .accessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive().toMinutes())
                        .authorizationCodeTimeToLive(tokenSettings.getAuthorizationCodeTimeToLive().toMinutes())
                        .refreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive().toMinutes())
                        .idTokenSignatureAlgorithm(Signature.from(tokenSettings.getIdTokenSignatureAlgorithm()))
                        .reuseRefreshTokens(tokenSettings.isReuseRefreshTokens())
                        .build();
    }

}
