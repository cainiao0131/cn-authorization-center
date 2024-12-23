package org.cainiao.authorizationcenter.config.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.entity.authorizationserver.ClientUser;
import org.cainiao.authorizationcenter.service.ClientUserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.util.Assert;

import java.security.Principal;
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

    private final ClientUserService clientUserService;

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
            .getByReflect(Principal.class.getName(), OAuth2AuthenticationToken.class);
        CnMap principalAttributes = CnMap.NEW();
        if (oAuth2AuthenticationToken != null) {
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
            if (oAuth2User != null) {
                principalAttributes = CnMap.toCnMap(oAuth2User.getAttributes());
            }
        }

        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = attributes
            .getByReflect(OAuth2AuthorizationRequest.class.getName(), OAuth2AuthorizationRequest.class);
        long cnUserId = Long.parseLong(authorization.getPrincipalName());
        /*
         * 授权服务器作为【三方（如飞书）】的 OAuth2 客户端进行【三方】登录期间，用 OAuth2UserService#loadUser() 获取用户在【三方】的用户信息
         * OAuth2UserService#loadUser() 只会在用户登录【授权服务器】时调用
         * 用户在【授权服务器】的会话未失效时，通过另一个未登录的【授权服务器 OAuth2 客户端】登录时，不会再调用 OAuth2UserService#loadUser()
         * 因此 ” 用户第一次访问某【授权服务器 OAuth2 客户端】时，为其生成【openId】 “ 不能在 OAuth2UserService#loadUser() 中执行
         * 而要在【授权服务器 OAuth2 客户端】通过 ID Token 换 user info 的端点完成
         * 也就是这里的 DynamicOidcUserInfoMapper#getClaimsRequestedByScope() 方法中
         */
        ClientUser clientUser = clientUserService.createIfFirstUse(cnUserId, oAuth2AuthorizationRequest.getClientId());
        principalAttributes.put("cn_open_id", clientUser.getOpenId());

        OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
        Map<String, Object> scopeRequestedClaims = getClaimsRequestedByScope(principalAttributes,
            claims, accessToken.getScopes());
        return new OidcUserInfo(scopeRequestedClaims);
    }

    private static Map<String, Object> getClaimsRequestedByScope(CnMap principalAttributes,
                                                                 Map<String, Object> claims,
                                                                 Set<String> requestedScopes) {
        Set<String> scopeRequestedClaimNames = new HashSet<>(32);
        /*
         * sub 表示 subject，主体名称，必须与 ID Token 的 Name 相同
         * 因为在 OIDC 登录过程中，ID Token 换用户信息时，OAuth2 客户端会校验，如不同客户端会抛异常，登录失败
         * 当用户已经用某个【授权服务器 OAuth2 客户端】登录过一次，授权服务器会话还没有失效时
         * 如果用户又访问另一个还没有会话的【授权服务器 OAuth2 客户端】，会重新走一遍 OIDC 登录流程
         * 由于用户在不同的【授权服务器 OAuth2 客户端】的 cn_open_id 是不同的
         * 因此不能用 cn_open_id 作为主体名称，应该用 cn_user_id 作为主体名称
         *
         * 但各个【授权服务器 OAuth2 客户端】应该用 cn_open_id 来标识用户，避免互相知道对方的用户标识造成安全问题
         * 应该将【授权服务器 OAuth2 客户端】的 OAuth2 数据库中 oauth2_client_registration 的 user_name_attribute_name 配置为 cn_open_id
         * 这样【授权服务器 OAuth2 客户端】构建主体时，主体名称就会使用 cn_open_id 了
         */
        scopeRequestedClaimNames.add(StandardClaimNames.SUB);
        scopeRequestedClaimNames.add("cn_open_id");

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
