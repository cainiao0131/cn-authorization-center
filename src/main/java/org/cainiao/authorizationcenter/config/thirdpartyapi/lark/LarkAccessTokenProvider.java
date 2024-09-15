package org.cainiao.authorizationcenter.config.thirdpartyapi.lark;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.config.httpclient.AccessTokenRepository;

import java.util.function.Supplier;

import static org.cainiao.authorizationcenter.config.login.oauth2client.Oauth2ClientSecurityFilterChainConfig.LARK_REGISTRATION_ID;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class LarkAccessTokenProvider implements Supplier<String> {

    private final AccessTokenRepository accessTokenRepository;

    @Override
    public String get() {
        return accessTokenRepository.getAccessToken(LARK_REGISTRATION_ID);
    }
}
