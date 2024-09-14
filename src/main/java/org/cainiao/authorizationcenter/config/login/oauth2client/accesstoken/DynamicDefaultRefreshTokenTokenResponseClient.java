package org.cainiao.authorizationcenter.config.login.oauth2client.accesstoken;

import org.cainiao.api.lark.dto.response.authenticateandauthorize.getaccesstokens.UserAccessTokenResponse;
import org.cainiao.api.lark.imperative.LarkApi;
import org.cainiao.authorizationcenter.config.thirdpartyapi.lark.LarkAppAccessTokenRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.cainiao.authorizationcenter.config.login.oauth2client.Oauth2ClientSecurityFilterChainConfig.LARK_REGISTRATION_ID;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class DynamicDefaultRefreshTokenTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2RefreshTokenGrantRequest> {

    private static final String INVALID_TOKEN_RESPONSE_ERROR_CODE = "invalid_token_response";
    private final LarkApi larkApi;
    private final LarkAppAccessTokenRepository larkAppAccessTokenRepository;

    private Converter<OAuth2RefreshTokenGrantRequest, RequestEntity<?>> requestEntityConverter =
        new ClientAuthenticationMethodValidatingRequestEntityConverter<>(
            new OAuth2RefreshTokenGrantRequestEntityConverter());

    private RestOperations restOperations;

    public DynamicDefaultRefreshTokenTokenResponseClient(LarkApi larkApi,
                                                         LarkAppAccessTokenRepository larkAppAccessTokenRepository) {
        this.larkApi = larkApi;
        this.larkAppAccessTokenRepository = larkAppAccessTokenRepository;
        RestTemplate restTemplate = new RestTemplate(
            Arrays.asList(new FormHttpMessageConverter(), new OAuth2AccessTokenResponseHttpMessageConverter()));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.restOperations = restTemplate;
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2RefreshTokenGrantRequest refreshTokenGrantRequest) {
        Assert.notNull(refreshTokenGrantRequest, "refreshTokenGrantRequest cannot be null");
        ClientRegistration clientRegistration = refreshTokenGrantRequest.getClientRegistration();
        if (LARK_REGISTRATION_ID.equals(clientRegistration.getRegistrationId())) {
            // 飞书刷新 access token
            return getTokenResponseForLark(refreshTokenGrantRequest, clientRegistration);
        }
        return getTokenResponseForDefault(refreshTokenGrantRequest);
    }

    private OAuth2AccessTokenResponse getTokenResponseForLark(OAuth2RefreshTokenGrantRequest refreshTokenGrantRequest,
                                                              ClientRegistration clientRegistration) {
        UserAccessTokenResponse response = larkApi.authenticateAndAuthorize().getAccessTokens()
            .refreshUserAccessToken(larkAppAccessTokenRepository.getCustomAppAppAccessToken(clientRegistration),
                refreshTokenGrantRequest.getRefreshToken().getTokenValue()).getBody();
        Assert.notNull(response, "tokenResponse cannot be null");
        UserAccessTokenResponse.TokenInfo tokenInfo = response.getData();
        return OAuth2AccessTokenResponse.withToken(tokenInfo.getAccessToken())
            .tokenType(OAuth2AccessToken.TokenType.BEARER)
            .expiresIn(tokenInfo.getExpiresIn())
            .scopes(Stream.of(tokenInfo.getScope().split(" ")).collect(Collectors.toSet()))
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }

    private OAuth2AccessTokenResponse getTokenResponseForDefault(OAuth2RefreshTokenGrantRequest refreshTokenGrantRequest) {
        RequestEntity<?> request = this.requestEntityConverter.convert(refreshTokenGrantRequest);
        ResponseEntity<OAuth2AccessTokenResponse> response = getResponse(request);
        OAuth2AccessTokenResponse tokenResponse = response.getBody();
        Assert.notNull(tokenResponse, "tokenResponse cannot be null");
        Set<String> scopes = tokenResponse.getAccessToken().getScopes();
        if (CollectionUtils.isEmpty(scopes) || tokenResponse.getRefreshToken() == null) {
            OAuth2AccessTokenResponse.Builder tokenResponseBuilder = OAuth2AccessTokenResponse
                .withResponse(tokenResponse);
            if (CollectionUtils.isEmpty(scopes)) {
                // As per spec, in Section 5.1 Successful Access Token Response
                // https://tools.ietf.org/html/rfc6749#section-5.1
                // If AccessTokenResponse.scope is empty, then default to the scope
                // originally requested by the client in the Token Request
                tokenResponseBuilder.scopes(refreshTokenGrantRequest.getAccessToken().getScopes());
            }
            if (tokenResponse.getRefreshToken() == null) {
                // Reuse existing refresh token
                tokenResponseBuilder.refreshToken(refreshTokenGrantRequest.getRefreshToken().getTokenValue());
            }
            tokenResponse = tokenResponseBuilder.build();
        }
        return tokenResponse;
    }

    private ResponseEntity<OAuth2AccessTokenResponse> getResponse(RequestEntity<?> request) {
        try {
            return this.restOperations.exchange(request, OAuth2AccessTokenResponse.class);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_TOKEN_RESPONSE_ERROR_CODE,
                "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: "
                    + ex.getMessage(),
                null);
            throw new OAuth2AuthorizationException(oauth2Error, ex);
        }
    }

    /**
     * Sets the {@link Converter} used for converting the
     * {@link OAuth2RefreshTokenGrantRequest} to a {@link RequestEntity} representation of
     * the OAuth 2.0 Access Token Request.
     *
     * @param requestEntityConverter the {@link Converter} used for converting to a
     *                               {@link RequestEntity} representation of the Access Token Request
     */
    public void setRequestEntityConverter(
        Converter<OAuth2RefreshTokenGrantRequest, RequestEntity<?>> requestEntityConverter) {
        Assert.notNull(requestEntityConverter, "requestEntityConverter cannot be null");
        this.requestEntityConverter = requestEntityConverter;
    }

    /**
     * Sets the {@link RestOperations} used when requesting the OAuth 2.0 Access Token
     * Response.
     *
     * <p>
     * <b>NOTE:</b> At a minimum, the supplied {@code restOperations} must be configured
     * with the following:
     * <ol>
     * <li>{@link HttpMessageConverter}'s - {@link FormHttpMessageConverter} and
     * {@link OAuth2AccessTokenResponseHttpMessageConverter}</li>
     * <li>{@link ResponseErrorHandler} - {@link OAuth2ErrorResponseErrorHandler}</li>
     * </ol>
     *
     * @param restOperations the {@link RestOperations} used when requesting the Access
     *                       Token Response
     */
    public void setRestOperations(RestOperations restOperations) {
        Assert.notNull(restOperations, "restOperations cannot be null");
        this.restOperations = restOperations;
    }
}
