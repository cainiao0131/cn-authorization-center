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
import org.cainiao.authorization.entity.BaseEntity;
import org.nutz.dao.entity.annotation.*;
import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 准许<br />
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
@Table("t_oauth2_authorization_consent")
@Comment("准许")
@Schema(name = "AuthorizationConsent", description = "准许")
public class AuthorizationConsent extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -2265389057732489226L;

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
    @ColDefine(notNull = true, width = 100, precision = 0)
    private String principalName;

    @Schema(description = "权力", requiredMode = RequiredMode.REQUIRED)
    @Column("authorities")
    @Comment("权力")
    @ColDefine(notNull = true, width = 1000, precision = 0)
    @Default
    private List<String> authorities = Lang.list();

    public OAuth2AuthorizationConsent toAuth2AuthorizationConsent() {
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(registeredClientId, principalName);
        authorities.forEach(a -> builder.authority(new SimpleGrantedAuthority(a)));
        return builder.build();
    }

    public static AuthorizationConsent from(OAuth2AuthorizationConsent consent) {
        return builder().registeredClientId(consent.getRegisteredClientId())
                        .principalName(consent.getPrincipalName())
                        .authorities(consent.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .build();
    }
}
