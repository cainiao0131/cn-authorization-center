package org.cainiao.authorizationcenter.config.authorizationserver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.Arrays;
import java.util.List;

import static org.cainiao.authorizationcenter.config.login.oauth2client.Oauth2ClientSecurityFilterChainConfig.LARK_REGISTRATION_ID;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class DynamicAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public DynamicAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
                                                     AuthenticationException exception) {
        String scopeString = request.getParameter("scope");
        String[] scopeArray;
        if (scopeString != null && (scopeArray = scopeString.split(" ")).length > 0) {
            List<String> scopes = Arrays.asList(scopeArray);
            if (scopes.contains("lark")) {
                return String.format("/oauth2/authorization/%s", LARK_REGISTRATION_ID);
            }
        }
        return getLoginFormUrl();
    }

}
