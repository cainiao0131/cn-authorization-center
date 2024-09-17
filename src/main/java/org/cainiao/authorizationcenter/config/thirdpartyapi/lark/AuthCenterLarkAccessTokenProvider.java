package org.cainiao.authorizationcenter.config.thirdpartyapi.lark;

import lombok.RequiredArgsConstructor;
import org.cainiao.api.lark.impl.util.provider.LarkAccessTokenProvider;
import org.cainiao.authorizationcenter.config.login.oauth2client.accesstoken.AccessTokenRepository;

import static org.cainiao.authorizationcenter.config.login.oauth2client.Oauth2ClientSecurityFilterChainConfig.LARK_REGISTRATION_ID;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class AuthCenterLarkAccessTokenProvider implements LarkAccessTokenProvider {

    private final AccessTokenRepository accessTokenRepository;

    @Override
    public String getAccessToken() {
        return accessTokenRepository.getAccessToken(LARK_REGISTRATION_ID);
    }
}
