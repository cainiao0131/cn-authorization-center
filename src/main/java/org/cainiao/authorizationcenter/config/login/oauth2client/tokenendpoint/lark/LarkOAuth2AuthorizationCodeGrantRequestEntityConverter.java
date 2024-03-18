package org.cainiao.authorizationcenter.config.login.oauth2client.tokenendpoint.lark;

import lombok.RequiredArgsConstructor;
import org.cainiao.api.lark.api.LarkApi;
import org.cainiao.api.lark.api.authenticateandauthorize.getaccesstokens.dto.response.AppAccessTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class LarkOAuth2AuthorizationCodeGrantRequestEntityConverter
    extends OAuth2AuthorizationCodeGrantRequestEntityConverter {

    private final LarkApi larkApi;

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        return new RequestEntity<>(
            convertParameters(authorizationGrantRequest),
            convertHeaders(authorizationGrantRequest),
            HttpMethod.POST,
            UriComponentsBuilder
                .fromUriString(authorizationGrantRequest.getClientRegistration().getProviderDetails().getTokenUri())
                .build().toUri());
    }

    private HttpHeaders convertHeaders(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();
        AppAccessTokenResponse appAccessTokenResponse = larkApi.authenticateAndAuthorize().getAccessTokens()
            .getCustomAppAppAccessToken(clientRegistration.getClientId(), clientRegistration.getClientSecret());
        headers.setBearerAuth(appAccessTokenResponse.getAppAccessToken());
        return headers;
    }

    protected Map<String, String> convertParameters(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
        parameters.put(OAuth2ParameterNames.CODE,
            authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        return parameters;
    }

}
