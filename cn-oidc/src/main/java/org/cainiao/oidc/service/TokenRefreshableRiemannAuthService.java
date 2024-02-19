package org.cainiao.oidc.service;

import com.auth0.jwt.interfaces.Claim;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.cainiao.auth.AuthUser;
import org.cainiao.auth.jwt.JWTGenerator;
import org.cainiao.auth.service.AbstractAuthService;
import org.cainiao.common.exception.BusinessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.cainiao.common.util.StringUtil.equalsIgnoreCase;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class TokenRefreshableRiemannAuthService extends AbstractAuthService {

    private static final String TOKEN_EXPIRED = "Token已过期!";
    private final JWTGenerator jwtGenerator;
    private final String domain;
    private final String tokenCookieName;
    private final String refreshTokenCookieName;
    private final String authorizationHeaderName;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @Override
    public AuthUser user() {
        String token = token();
        String refreshToken = refreshToken();
        if (Strings.isBlank(token) && Strings.isBlank(refreshToken)) {
            throw BusinessException.create(new AuthException("用户未登录!"));
        }
        if (!jwtGenerator.verify(token)) {
            return tryRefreshToken(refreshToken);
        }
        Map<String, Claim> claims = jwtGenerator.claims(token);
        return claims2User(token, refreshToken, claims);
    }

    private AuthUser claims2User(String token, String refreshToken, Map<String, Claim> claims) {
        return AuthUser.builder()
            .userName(jwtGenerator.verifiedSubject(token))
            .fullName(claim2String(claims.get("fullName")))
            .avatar(claim2String(claims.get("avatar")))
            .email(claim2String(claims.get("email")))
            .mobile(claim2String(claims.get("mobile")))
            .sex(claim2Class(claims.get("sex"), AuthUser.SexEnum.class))
            .roles(claim2List(claims.get("roles"), String.class))
            .permissions(claim2List(claims.get("permissions"), String.class))
            .token(token)
            .refreshToken(refreshToken)
            .build();
    }

    private <T> List<T> claim2List(Claim claim, Class<T> clazz) {
        return claim == null ? null : claim.asList(clazz);
    }

    private <T> T claim2Class(Claim claim, Class<T> clazz) {
        return claim == null ? null : claim.as(clazz);
    }

    private String claim2String(Claim claim) {
        return claim == null ? null : claim.asString();
    }

    /**
     * 尝试刷新token
     *
     * @param refreshToken refreshToken
     * @return AuthUser
     */
    private AuthUser tryRefreshToken(String refreshToken) {
        if (!jwtGenerator.verify(refreshToken)) {
            throw BusinessException.create(new AuthException(TOKEN_EXPIRED)); // refreshToken也过期了
        }
        Map<String, Claim> claims = jwtGenerator.claims(refreshToken);
        AuthUser user = claims2User(null, null, claims);
        Map<String, Object> newClaims = new HashMap<>();
        newClaims.put("userName", user.getUserName());
        newClaims.put("fullName", user.getFullName());
        newClaims.put("email", user.getEmail());
        newClaims.put("avatar", user.getAvatar());
        newClaims.put("mobile", user.getMobile());
        newClaims.put("sex", user.getSex().getName());
        newClaims.put("roles", user.getRoles());
        newClaims.put("permissions", user.getPermissions());
        newClaims.putAll(user.getExtInfo());
        String token = jwtGenerator.token(user.getUserName(), newClaims, 30, TimeUnit.MINUTES);
        refreshToken = jwtGenerator.token(user.getUserName(), newClaims, 60, TimeUnit.MINUTES);

        // 刷新token通过cookie带回去

        addCookie(response, tokenCookieName, token, 30, domain);
        addCookie(response, refreshTokenCookieName, refreshToken, 60, domain);

        return user;
    }

    private void addCookie(HttpServletResponse response, String name, String value, int age, String domain) {
        Cookie cookie = new Cookie(name, value);// 半小时
        cookie.setPath("/");
        cookie.setMaxAge((int) TimeUnit.MINUTES.toMillis(age));
        cookie.setDomain(domain);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    @Override
    public String userName() {
        return user().getUserName();
    }

    @Override
    public List<String> roles() {
        return user().getRoles();
    }

    @Override
    public List<String> permissions() {
        return user().getPermissions();
    }

    @Override
    public String token() {
        String token = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie ele : cookies) {
                if (equalsIgnoreCase(ele.getName(), authorizationHeaderName)
                    || equalsIgnoreCase(ele.getName(), tokenCookieName))
                {
                    token = ele.getValue();
                    if (Strings.isNotBlank(token)) {
                        break;
                    }
                }
            }
        }
        if (Strings.isBlank(token)) {
            token = getRequest().getHeader(authorizationHeaderName);
        }
        if (Strings.isBlank(token)) {
            token = getRequest().getParameter(authorizationHeaderName);
        }
        return token;
    }

    @Override
    public String refreshToken() {
        String refreshToken = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie ele : cookies) {
                if (equalsIgnoreCase(ele.getName(), refreshTokenCookieName)) {
                    refreshToken = ele.getValue();
                    if (Strings.isNotBlank(refreshToken)) {
                        break;
                    }
                }
            }
        }
        return refreshToken;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

}
