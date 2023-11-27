package org.cainiao.oidc;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.cainiao.auth.AuthUser;
import org.cainiao.auth.service.AuthService;
import org.cainiao.oidc.callback.CallbackHandler;
import org.cainiao.oidc.service.OpenIDConnectService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth2/oidc")
@Tag(name = "oidc", description = "Open ID Connect")
public class OpenIDConnectController {

    private final OpenIDConnectService openIDConnectService;
    private final CallbackHandler callbackHandler;
    private final AuthService authService;

    @GetMapping("login-url")
    @Operation(summary = "获取登录地址")
    public String loginUrl(
        @Parameter(description = "state") @RequestParam(value = "state", required = false) String state,
        @Parameter(description = "nonce") @RequestParam(value = "nonce", required = false) String nonce)
    {
        return openIDConnectService.loginUrl(state, nonce);
    }

    @GetMapping("login")
    @Hidden
    public void login(@RequestParam(value = "state", required = false) String state,
        @RequestParam(value = "nonce", required = false) String nonce, HttpServletResponse response) throws IOException
    {
        response.sendRedirect(openIDConnectService.loginUrl(state, nonce));
    }

    @GetMapping("logout-url")
    @Operation(summary = "获取登出地址")
    public String logoutUrl() {
        return openIDConnectService.logoutUrl();
    }

    @GetMapping("logout")
    @Hidden
    public void logout(HttpServletResponse response) throws IOException {
        response.sendRedirect(openIDConnectService.logoutUrl());
    }

    @GetMapping("callback")
    @Hidden
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        callbackHandler.handle(request, response);
    }

    @PostMapping("login")
    @Operation(summary = "登录")
    public AuthUser login(
        @Parameter(description = "code") @RequestParam(value = "code", required = false) String code)
    {
        return openIDConnectService.login(code);
    }

    @GetMapping("current-user")
    @Operation(summary = "当前登录用户")
    public AuthUser currentUser() {
        return authService.user();
    }

}
