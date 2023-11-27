package org.cainiao.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.regex.Pattern;

import static org.cainiao.common.util.StringUtil.equalsIgnoreCase;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public abstract class AbstractAuthService implements AuthService {

    public static final String AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_KEY = "token";
    public static final String AUTHORIZATION_COOKIE_REFRESHTOKEN = "refreshToken";

    /**
     *
     * @return http 请求
     */
    public abstract HttpServletRequest getRequest();

    @Override
    public boolean authentication(List<String> withoutAuthenticationUrlRegulars) {
        return skip() || pass(getRequest().getRequestURI(), withoutAuthenticationUrlRegulars) || user() != null;
    }

    /**
     * @param uri
     *            请求地址
     * @param withoutAuthenticationUrlRegulars
     *            放行url正则
     * @return 是否放行
     */
    protected boolean pass(String uri, List<String> withoutAuthenticationUrlRegulars) {
        for (String regular : withoutAuthenticationUrlRegulars) {
            if (Pattern.matches(regular, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String token() {
        String token = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie ele : cookies) {
                if (equalsIgnoreCase(ele.getName(), AUTHORIZATION_KEY) || equalsIgnoreCase(ele.getName(), TOKEN_KEY)) {
                    token = ele.getValue();
                    if (Strings.isNotBlank(token)) {
                        break;
                    }
                }
            }
        }
        if (Strings.isBlank(token)) {
            token = getRequest().getHeader(AUTHORIZATION_KEY);
        }
        if (Strings.isBlank(token)) {
            token = getRequest().getParameter(AUTHORIZATION_KEY);
        }
        return token;
    }

    @Override
    public String refreshToken() {
        String refreshToken = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null) {
            for (Cookie ele : cookies) {
                if (equalsIgnoreCase(ele.getName(), AUTHORIZATION_COOKIE_REFRESHTOKEN)) {
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
    public boolean skip() {
        return false;
    }

}
