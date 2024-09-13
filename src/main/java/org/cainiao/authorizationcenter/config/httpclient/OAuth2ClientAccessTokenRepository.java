package org.cainiao.authorizationcenter.config.httpclient;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 授权服务器作为【飞书】的 OAuth2 客户端登录后，从维护的登录会话中获取【飞书】的 access token<br/>
 * 由于当前应用不是【飞书商店应用】因此非企业内用户无法登录<br/>
 * TODO 所以暂时没有用这个方案，目前未登录用户也能调用飞书接口，集中维护一个 GlobalAccessTokenRepository 持续刷新的 access token
 */
@Component
@RequiredArgsConstructor
public class OAuth2ClientAccessTokenRepository implements AccessTokenRepository {

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Override
    public String getAccessToken(String registrationId) {
        /*
         * 支持匿名访问飞书资源服务器
         * 如果已登录，则用登录用户授予的 access token 访问飞书资源
         * 如果没有登录，则用公共用户授予的 access token 访问飞书资源
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService
            .loadAuthorizedClient(registrationId, isAnonymous(authentication) ? "1" : authentication.getName());
        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientManager.authorize(OAuth2AuthorizeRequest
            .withAuthorizedClient(oAuth2AuthorizedClient)
            .principal(authentication)
            .build());
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        return authorizedClient.getAccessToken().getTokenValue();
    }

    private boolean isAnonymous(Authentication authentication) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            return true;
        }
        if (authentication == null) {
            return true;
        }
        return !authentication.isAuthenticated();
    }
}
