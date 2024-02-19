package org.cainiao.auth.jwt;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface JWTGenerator {

    /**
     * 生成token
     *
     * @param subject
     *            主题
     * @param claims
     *            附加信息
     * @return token
     */
    String token(String subject, Map<String, ?> claims);

    /**
     * 生成 token
     *
     * @param subject
     *            主题
     * @param claims
     *            附加信息
     * @param term
     *            有效期
     * @param unit
     *            有效期单位
     * @return token
     */
    String token(String subject, Map<String, ?> claims, long term, TimeUnit unit);

    /**
     * 生成token
     *
     * @param subject
     *            主题
     * @return token
     */
    String token(String subject);

    /**
     * 生成刷新令牌
     *
     * @param subject
     *            主题
     * @return refresh token
     */
    String refreshToken(String subject);

    /**
     * 生成token
     *
     * @param subject
     *            主题
     * @param claims
     *            附加信息
     * @param jti
     *            jti claim
     * @param kid
     *            kid claim
     * @return token
     */
    String token(String subject, Map<String, ?> claims, String jti, String kid);

    /**
     * 验证token
     *
     * @param token
     *            token
     * @return 是否同验证
     */
    boolean verify(String token);

    /**
     * 获取主题
     *
     * @param token
     *            token
     * @return 主题
     */
    String subject(String token);

    /**
     * 验证并获取主题
     *
     * @param token
     *            token
     * @return 主题
     */
    String verifiedSubject(String token);

    /**
     * 获取附加信息
     *
     * @param token
     *            token
     * @param name
     *            claim name
     * @return claim
     */
    Claim claim(String token, String name);

    Map<String, Claim> claims(String token);

}
