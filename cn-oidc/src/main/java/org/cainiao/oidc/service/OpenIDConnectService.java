package org.cainiao.oidc.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.cainiao.auth.AuthUser;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.oidc.config.webclient.WebClientService;
import org.cainiao.oidc.domain.OpenIDConnectClient;
import org.cainiao.oidc.domain.Token;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

import static org.cainiao.common.util.CollectionUtil.*;
import static org.cainiao.common.util.RandomUtil.UU16;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Slf4j
public class OpenIDConnectService {

    private final OpenIDConnectClient client;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final WebClientService webClientService;

    public OpenIDConnectService(String discoveryUrl, String clientId, String clientSecret,
                                String redirectUrl, WebClientService webClientService) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.webClientService = webClientService;
        this.client = discovery(discoveryUrl);
    }

    private OpenIDConnectClient discovery(String discoveryUrl) {
        try {
            return webClientService.get(discoveryUrl, OpenIDConnectClient.class);
        } catch (WebClientResponseException e) {
            log.error("discovery 请求失败，discoveryUrl = {}", discoveryUrl, e);
            // 抛出异常或进行其他处理
            throw BusinessException.create("oidc 发现地址: %s不可用", discoveryUrl);
        }
    }

    /**
     * 获取 token 信息
     *
     * @param code 授权码
     * @return token 信息
     */
    public Token token(String code) {
        String tokenEndpoint = client.getTokenEndpoint();
        try {
            return webClientService.postBody("client_id", clientId)
                .put("client_secret", clientSecret)
                .put("code", code)
                .put("grant_type", "authorization_code")
                .put("redirect_uri", redirectUrl)
                .post(tokenEndpoint, Token.class);
        } catch (WebClientResponseException e) {
            log.error("获取 token 信息失败，tokenEndpoint = {}", tokenEndpoint, e);
            return null;
        }
    }

    /**
     * 用 access token 换取用户信息
     *
     * @param token access token
     * @return 用户信息
     */
    public AuthUser user(String token) {
        String userinfoEndpoint = client.getUserinfoEndpoint();
        try {
             Map<?, ?> responseMap = webClientService.get(userinfoEndpoint,
                "Authorization", String.format("Bearer %s", token), Map.class);
            return AuthUser.builder()
                .userName(getString(responseMap, "sub"))
                .roles(getAsList(responseMap, "roles", String.class))
                .permissions(getAsList(responseMap, "permissions", String.class))
                .mobile(getString(responseMap, "phone_number"))
                .fullName(getString(responseMap, "nickname"))
                .email(getString(responseMap, "email"))
                .sex(AuthUser.getSex(getString(responseMap, "gender")))
                .avatar(getString(responseMap, "avatar"))
                .extInfo(omit(responseMap,
                    "sub", "roles", "permissions", "phone_number", "nickname", "email", "gender", "avatar"))
                .build();
        } catch (WebClientResponseException e) {
            log.error("用 access token 换取用户信息失败，userinfoEndpoint = {}", userinfoEndpoint, e);
            return null;
        }
    }

    /**
     * 使用授权码进行登录(获取用户信息)
     *
     * @param code 授权码
     * @return AuthUser
     */
    public AuthUser login(String code) {
        Token token = token(code);
        if (token == null || Strings.isBlank(token.getAccessToken())) {
            throw BusinessException.create("无效的授权码: %s", code);
        }
        return user(token.getAccessToken());
    }

    /**
     * 登录地址
     *
     * @param state
     *            state
     * @param nonce
     *            nonce
     * @return 登录地址
     */
    public String loginUrl(String state, String nonce) {
        return String.format(
            "%s?response_type=code&client_id=%s&scope=openid profile&state=%s&redirect_uri=%s&nonce=%s",
            client.getAuthorizationEndpoint(),
            clientId,
            Strings.isBlank(state) ? UU16() : state,
            redirectUrl,
            Strings.isBlank(nonce) ? UU16() : nonce);
    }

    /**
     * 登录地址
     *
     * @return 登录地址
     */
    public String loginUrl() {
        return loginUrl(null, null);
    }

    /**
     * 登出地址
     *
     * @return 登录地址
     */
    public String logoutUrl() {
        return String.format("%s?client_id=%s", client.getEndSessionEndpoint(), clientId);
    }

}
