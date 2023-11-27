package org.cainiao.oidc.callback;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cainiao.auth.AuthUser;
import org.cainiao.auth.jwt.JWTGenerator;
import org.cainiao.common.util.JsonUtil;
import org.cainiao.common.util.exception.BusinessException;
import org.cainiao.oidc.service.OpenIDConnectService;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
@Slf4j
public class CookieCallbackHandler implements CallbackHandler {

    private final String loginSuccessUrl;
    private final String tokenCookieName;
    private final String refreshTokenCookieName;
    private final JWTGenerator jwtGenerator;
    private final OpenIDConnectService openIDConnectService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        // 基于cookie的登录会话保持回跳
        AuthUser user = openIDConnectService.login(code(request, response));
        log.debug("login user: {}", JsonUtil.toJson(user));
        if (user == null) {
            // 处理失败
            throw BusinessException.create("login fail user is null");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", user.getUserName());
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail());
        claims.put("avatar", user.getAvatar());
        claims.put("mobile", user.getMobile());
        claims.put("sex", user.getSex().getName());
        claims.put("roles", user.getRoles());
        claims.put("permissions", user.getPermissions());
        claims.putAll(user.getExtInfo());
        String token = jwtGenerator.token(user.getUserName(), claims, 30, TimeUnit.MINUTES);
        String refreshToken = jwtGenerator.token(user.getUserName(), claims, 60, TimeUnit.MINUTES);
        try {
            URL urlToRedirect = new URL(loginSuccessUrl);
            addCookie(response, tokenCookieName, token, 30, urlToRedirect);
            addCookie(response, refreshTokenCookieName, refreshToken, 60, urlToRedirect);
            response.sendRedirect(loginSuccessUrl);
        } catch (IOException e) {
            throw BusinessException.create(e);
        }
    }

    private void addCookie(HttpServletResponse response, String name, String value, int age, URL urlToRedirect) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.MINUTES.toMillis(age));
        cookie.setDomain(urlToRedirect.getHost());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

}
