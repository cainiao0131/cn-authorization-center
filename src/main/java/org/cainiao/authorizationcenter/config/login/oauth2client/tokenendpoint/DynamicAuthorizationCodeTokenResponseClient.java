package org.cainiao.authorizationcenter.config.login.oauth2client.tokenendpoint;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 为了在用 code 换飞书 access token 时，先获取 appAccessToken 并设置到请求头<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class DynamicAuthorizationCodeTokenResponseClient
    implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private static final String INVALID_TOKEN_RESPONSE_ERROR_CODE = "invalid_token_response";

    private final Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>>
        defaultDelegateConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    private final Map<String, Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>>>
        delegateConverterRegistry = new HashMap<>();
    private final RestOperations defaultRestOperations;
    private final Map<String, RestOperations> restOperationsRegistry = new HashMap<>();

    public DynamicAuthorizationCodeTokenResponseClient() {
        RestTemplate defaultRestTemplate = new RestTemplate(Arrays
            .asList(new FormHttpMessageConverter(), new OAuth2AccessTokenResponseHttpMessageConverter()));
        defaultRestTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.defaultRestOperations = defaultRestTemplate;
    }

    public DynamicAuthorizationCodeTokenResponseClient registerDelegateConverter(
        String registrationId, Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> delegateConverter) {
        delegateConverterRegistry.put(registrationId, delegateConverter);
        return this;
    }

    public DynamicAuthorizationCodeTokenResponseClient registerRestOperations(
        String registrationId, RestOperations restOperations) {
        restOperationsRegistry.put(registrationId, restOperations);
        return this;
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(
        OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        String registrationId = authorizationCodeGrantRequest.getClientRegistration().getRegistrationId();

        Assert.notNull(authorizationCodeGrantRequest, "authorizationCodeGrantRequest cannot be null");
        RequestEntity<?> request = convert(authorizationCodeGrantRequest);

        ResponseEntity<OAuth2AccessTokenResponse> response = getResponse(request, registrationId);
        // As per spec, in Section 5.1 Successful Access Token Response
        // https://tools.ietf.org/html/rfc6749#section-5.1
        // If AccessTokenResponse.scope is empty, then we assume all requested scopes were
        // granted.
        // However, we use the explicit scopes returned in the response (if any).
        OAuth2AccessTokenResponse tokenResponse = response.getBody();
        Assert.notNull(tokenResponse,
            "The authorization server responded to this Authorization Code grant request with an empty body; as such, "
                + "it cannot be materialized into an OAuth2AccessTokenResponse instance. "
                + "Please check the HTTP response code in your server logs for more details.");
        return tokenResponse;
    }

    private ResponseEntity<OAuth2AccessTokenResponse> getResponse(RequestEntity<?> request,
                                                                  String registrationId) {
        try {
            return Optional.ofNullable(restOperationsRegistry.get(registrationId)).orElse(defaultRestOperations)
                .exchange(request, OAuth2AccessTokenResponse.class);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_TOKEN_RESPONSE_ERROR_CODE,
                "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: "
                    + ex.getMessage(),
                null);
            throw new OAuth2AuthorizationException(oauth2Error, ex);
        }
    }

    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest grantRequest) {
        ClientRegistration clientRegistration = grantRequest.getClientRegistration();
        ClientAuthenticationMethod clientAuthenticationMethod = clientRegistration.getClientAuthenticationMethod();
        String registrationId = clientRegistration.getRegistrationId();
        boolean supportedClientAuthenticationMethod = clientAuthenticationMethod.equals(ClientAuthenticationMethod.NONE)
            || clientAuthenticationMethod.equals(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            || clientAuthenticationMethod.equals(ClientAuthenticationMethod.CLIENT_SECRET_POST);
        Assert.isTrue(supportedClientAuthenticationMethod, () -> String.format(
            "This class supports `client_secret_basic`, `client_secret_post`, and `none` by default. "
                + "Client [%s] is using [%s] instead. Please use a supported client authentication method, "
                + "or use `setRequestEntityConverter` to supply an instance that supports [%s].",
            registrationId, clientAuthenticationMethod, clientAuthenticationMethod));
        return Optional.ofNullable(delegateConverterRegistry.get(registrationId)).orElse(defaultDelegateConverter)
            .convert(grantRequest);
    }

}
