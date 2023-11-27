package org.cainiao.auth.service;

import org.cainiao.auth.AuthUser;

import java.util.List;

public interface AuthService {

    List<String> roles();

    List<String> permissions();

    AuthUser user();

    String token();

    String refreshToken();

    String userName();

    boolean skip();

    /**
     * 认证检查
     *
     * @param withoutAuthenticationUrlRegulars
     *            不需要检查的url正则表达式
     * @return 认证检查通过状态
     */
    boolean authentication(List<String> withoutAuthenticationUrlRegulars);

}
