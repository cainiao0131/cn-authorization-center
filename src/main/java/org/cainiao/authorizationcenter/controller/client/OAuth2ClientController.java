package org.cainiao.authorizationcenter.controller.client;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dto.request.OAuth2Client;
import org.cainiao.authorizationcenter.service.RegisteredClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RestController
@RequestMapping("/oauth2/clients")
@RequiredArgsConstructor
@Tag(name = "OAuth2Client", description = "管理 OAuth2 客户端")
public class OAuth2ClientController {

    private final RegisteredClientService registeredClientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerClient(
        @Parameter(description = "OAuth2 客户端") @RequestBody OAuth2Client client) {

        registeredClientService.registerClient(client);
    }

    @PutMapping("/{clientId}")
    public void updateClient(
        @Parameter(description = "OAuth2 客户端的 clientId") @PathVariable String clientId,
        @Parameter(description = "OAuth2 客户端") @RequestBody OAuth2Client client) {

        registeredClientService.updateClient(clientId, client);
    }
}
