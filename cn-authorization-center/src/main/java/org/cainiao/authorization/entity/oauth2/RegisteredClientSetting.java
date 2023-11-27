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
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.io.Serial;
import java.util.Arrays;
import java.util.Optional;

/**
 * 认证客户端设置<br />
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
@Table("t_oauth2_registered_client_setting")
@Comment("认证客户端设置")
@Schema(name = "RegisteredClientSetting", description = "认证客户端设置")
public class RegisteredClientSetting extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1466344027975694999L;

    @Schema(description = "客户端id", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("client_id")
    @Comment("客户端id")
    @ColDefine(notNull = true, width = 100, precision = 0)
    private String clientId;

    @Schema(description = "jwkSetUrl", requiredMode = RequiredMode.AUTO)
    @Column("jwk_set_url")
    @Comment("jwkSetUrl")
    @ColDefine(width = 100, precision = 0)
    private String jwkSetUrl;

    @Schema(description = "是否要求授权许可", requiredMode = RequiredMode.AUTO)
    @Column("require_authorization_consent")
    @Comment("是否要求授权许可")
    @ColDefine(width = 1, precision = 0)
    @Default
    private boolean requireAuthorizationConsent = false;

    @Schema(description = "是否要求ProofKey", requiredMode = RequiredMode.AUTO)
    @Column("require_proof_key")
    @Comment("是否要求ProofKey")
    @ColDefine(width = 1, precision = 0)
    @Default
    private boolean requireProofKey = false;

    @Schema(description = "token端点签名算法", requiredMode = RequiredMode.AUTO)
    @Column("token_endpoint_authentication_signing_algorithm")
    @Comment("token端点签名算法")
    @ColDefine(width = 20, precision = 0)
    private JwsAlgorithm tokenEndpointAuthenticationSigningAlgorithm;

    @Schema(description = "是否启用企业微信", requiredMode = RequiredMode.AUTO)
    @Column("work_wechat_enabled")
    @Comment("是否启用企业微信")
    @ColDefine(width = 1, precision = 0)
    private boolean workwechatEnabled;

    @Schema(description = "企业微信回调域名", requiredMode = RequiredMode.AUTO)
    @Column("workwechat_redirect_domain")
    @Comment("企业微信回调域名")
    @ColDefine(width = 100, precision = 0)
    private String workwechatRedirectDomain;

    @JsonField
    public Codebook getTokenEndpointAuthenticationSigningAlgorithmInfo() {
        return Optional.of(getTokenEndpointAuthenticationSigningAlgorithm()).orElse(JwsAlgorithm.RS256).build();
    }

    public void setTokenEndpointAuthenticationSigningAlgorithmInfo(Codebook sexInfo) {
        setTokenEndpointAuthenticationSigningAlgorithm(JwsAlgorithm.valueOf(sexInfo.getName()));
    }

    @Getter
    @AllArgsConstructor
    public enum JwsAlgorithm implements ICodeBook {
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
        PS512("PS512", SignatureAlgorithm.PS512),
        /**
         * 
         */
        HS256("HS256", MacAlgorithm.HS256),
        /**
         * 
         */
        HS384("HS384", MacAlgorithm.HS384),
        /**
         * 
         */
        HS512("HS512", MacAlgorithm.HS512);

        final String code;

        final org.springframework.security.oauth2.jose.jws.JwsAlgorithm algorithm;

        @Override
        public String getDescription() {
            return code;
        }

        public static JwsAlgorithm from(org.springframework.security.oauth2.jose.jws.JwsAlgorithm signatureAlgorithm) {
            return Arrays.stream(values()).filter(a -> Strings.equals(signatureAlgorithm.getName(), a.getAlgorithm().getName())).findFirst().orElse(RS512);
        }

    }

    public static final String WORKWECHAT_ENABLED_KEY = "workwechatEnabled";
    public static final String WORKWECHAT_REDIRECT_DOMAIN_KEY = "workwechatRedirectDomain";

    public ClientSettings toClientSettings() {
        ClientSettings.Builder builder = ClientSettings.builder()
                                                       .requireAuthorizationConsent(requireAuthorizationConsent)
                                                       .requireProofKey(requireProofKey)
                                                       .setting(WORKWECHAT_ENABLED_KEY, workwechatEnabled);
        if (Strings.isNotBlank(jwkSetUrl)) {
            builder.jwkSetUrl(jwkSetUrl);
        }
        if (tokenEndpointAuthenticationSigningAlgorithm != null) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(tokenEndpointAuthenticationSigningAlgorithm.getAlgorithm());
        }
        if (Strings.isNotBlank(workwechatRedirectDomain)) {
            builder.setting(WORKWECHAT_REDIRECT_DOMAIN_KEY, workwechatRedirectDomain);
        }
        return builder.build();
    }

    public static RegisteredClientSetting from(ClientSettings clientSettings, String clientId) {
        Boolean workwechatEnabled = clientSettings.getSetting(WORKWECHAT_ENABLED_KEY);
        return builder()
                        .clientId(clientId)
                        .jwkSetUrl(clientSettings.getJwkSetUrl())
                        .requireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent())
                        .requireProofKey(clientSettings.isRequireProofKey())
                        .tokenEndpointAuthenticationSigningAlgorithm(JwsAlgorithm.from(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm()))
                        .workwechatEnabled(workwechatEnabled != null && workwechatEnabled)
                        .workwechatRedirectDomain(clientSettings.getSetting(WORKWECHAT_REDIRECT_DOMAIN_KEY))
                        .build();
    }

}
