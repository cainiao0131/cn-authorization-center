package org.cainiao.oidc.callback;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.cainiao.common.util.exception.BusinessException;

import java.io.IOException;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class RedirectToFrontCallbackHandler implements CallbackHandler {

    private final String loginPage;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(String.format("%s%scode=%s&state=%s", loginPage,
                loginPage.contains("?") ? "&" : "?", code(request, response), state(request, response)));
        } catch (IOException e) {
            throw BusinessException.create(e);
        }
    }

}
