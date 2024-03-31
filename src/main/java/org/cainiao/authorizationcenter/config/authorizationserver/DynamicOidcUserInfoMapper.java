package org.cainiao.authorizationcenter.config.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;

import static org.cainiao.common.util.MapUtil.CnMap;

/**
 * OIDC 流程中，OAuth2 客户端用 ID Token 到授权服务器换用户信息时，通过上下文构建要返回给客户端的用户信息<br />
 * <ol>
 *     <li>
 *         获取的用户信息调整，因为像【飞书】这样的三方登录，claims 的名称并不能和 Spring Security 默认的名称对应上，
 *         有些信息在 principle 的属性中
 *     </li>
 * </ol>
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class DynamicOidcUserInfoMapper implements Function<OidcUserInfoAuthenticationContext, OidcUserInfo> {

    private static final List<String> EMAIL_CLAIMS = Arrays.asList(
        StandardClaimNames.EMAIL,
        StandardClaimNames.EMAIL_VERIFIED
    );
    private static final List<String> PHONE_CLAIMS = Arrays.asList(
        StandardClaimNames.PHONE_NUMBER,
        StandardClaimNames.PHONE_NUMBER_VERIFIED
    );
    private static final List<String> PROFILE_CLAIMS = Arrays.asList(
        StandardClaimNames.NAME,
        StandardClaimNames.FAMILY_NAME,
        StandardClaimNames.GIVEN_NAME,
        StandardClaimNames.MIDDLE_NAME,
        StandardClaimNames.NICKNAME,
        StandardClaimNames.PREFERRED_USERNAME,
        StandardClaimNames.PROFILE,
        StandardClaimNames.PICTURE,
        StandardClaimNames.WEBSITE,
        StandardClaimNames.GENDER,
        StandardClaimNames.BIRTHDATE,
        StandardClaimNames.ZONEINFO,
        StandardClaimNames.LOCALE,
        StandardClaimNames.UPDATED_AT
    );

    @Override
    public OidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
        OAuth2Authorization authorization = authenticationContext.getAuthorization();
        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = authorization.getToken(OidcIdToken.class);
        Assert.notNull(oidcIdToken, "oidcIdToken cannot be null");
        OidcIdToken idToken = oidcIdToken.getToken();

        Map<String, Object> claims = new HashMap<>(idToken.getClaims());

        CnMap attributes = CnMap.toCnMap(authorization.getAttributes());
        OAuth2AuthenticationToken oAuth2AuthenticationToken = attributes
            .get("java.security.Principal", false, OAuth2AuthenticationToken.class);
        CnMap principalAttributes = CnMap.NEW();
        if (oAuth2AuthenticationToken != null) {
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
            if (oAuth2User != null) {
                principalAttributes = CnMap.toCnMap(oAuth2User.getAttributes());
            }
        }

        OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
        Map<String, Object> scopeRequestedClaims = getClaimsRequestedByScope(principalAttributes,
            claims, accessToken.getScopes());
        return new OidcUserInfo(scopeRequestedClaims);
    }

    private static Map<String, Object> getClaimsRequestedByScope(CnMap principalAttributes,
                                                                 Map<String, Object> claims,
                                                                 Set<String> requestedScopes) {
        Set<String> scopeRequestedClaimNames = new HashSet<>(32);
        scopeRequestedClaimNames.add(StandardClaimNames.SUB);

        if (requestedScopes.contains(OidcScopes.ADDRESS)) {
            scopeRequestedClaimNames.add(StandardClaimNames.ADDRESS);
        }
        if (requestedScopes.contains(OidcScopes.EMAIL)) {
            scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PHONE)) {
            scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
        }
        if (requestedScopes.contains(OidcScopes.PROFILE)) {
            scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
        }

        Map<String, Object> requestedClaims = new HashMap<>(principalAttributes.putAll(claims).data());
        requestedClaims.keySet().removeIf(claimName -> !scopeRequestedClaimNames.contains(claimName));

        return requestedClaims;
    }
}
