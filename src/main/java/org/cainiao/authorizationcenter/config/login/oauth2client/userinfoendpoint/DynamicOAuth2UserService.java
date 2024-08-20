package org.cainiao.authorizationcenter.config.login.oauth2client.userinfoendpoint;

import org.cainiao.authorizationcenter.service.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownContentTypeException;

import java.util.*;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class DynamicOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";

    private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";

    private static final String INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response";

    private static final ParameterizedTypeReference<Map<String, Object>>
        PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<>() {
    };

    private Converter<OAuth2UserRequest, RequestEntity<?>>
        requestEntityConverter = new OAuth2UserRequestEntityConverter();

    private final RestOperations defaultRestOperations;
    private final Map<String, RestOperations> restOperationsRegistry = new HashMap<>();

    public DynamicOAuth2UserService(UserService userService_) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        this.defaultRestOperations = restTemplate;
        this.userService = userService_;
    }

    public DynamicOAuth2UserService registerRestOperations(String registrationId, RestOperations restOperations) {
        restOperationsRegistry.put(registrationId, restOperations);
        return this;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");
        if (!StringUtils
            .hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) {
            OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_INFO_URI_ERROR_CODE,
                "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: "
                    + userRequest.getClientRegistration().getRegistrationId(),
                null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();
        if (!StringUtils.hasText(userNameAttributeName)) {
            OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE,
                "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: "
                    + userRequest.getClientRegistration().getRegistrationId(),
                null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }
        RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
        ResponseEntity<Map<String, Object>> response = getResponse(userRequest, request);
        Map<String, Object> userAttributes = response.getBody();
        Assert.notNull(userAttributes, "userAttributes cannot be null");

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new OAuth2UserAuthority(userAttributes));
        OAuth2AccessToken token = userRequest.getAccessToken();
        for (String authority : token.getScopes()) {
            authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
        }

        /*
         * 用户首次通过【三方（如飞书）】登录平台，自动创建平台用户
         * 为了能够让多个【应用】同时登录
         * 之后【应用】调用【授权中心】的 OIDC 接口，通过 ID Token 换取 user_info 时
         * ID Token 中的主体名称（"sub"）的值必须是【平台用户 ID】
         * 因此必须在这里构建 DefaultOAuth2User 时，就要让 userNameAttributeName 对应的值是【平台用户 ID】
         * createIfFirstLogin() 中会将获取到的【平台用户 ID】设置为 cn_user_id 属性
         * 将【授权服务器】的数据库中的 oauth2_client_registration 的 user_name_attribute_name 配置为 cn_user_id
         */
        userService.createIfFirstLogin(userRequest, userAttributes);

        return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
    }

    private ResponseEntity<Map<String, Object>> getResponse(OAuth2UserRequest userRequest, RequestEntity<?> request) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        try {
            return Optional.ofNullable(restOperationsRegistry.get(registrationId)).orElse(defaultRestOperations)
                .exchange(request, PARAMETERIZED_RESPONSE_TYPE);
        } catch (OAuth2AuthorizationException ex) {
            OAuth2Error oauth2Error = ex.getError();
            StringBuilder errorDetails = new StringBuilder();
            errorDetails.append("Error details: [");
            errorDetails.append("UserInfo Uri: ")
                .append(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
            errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
            if (oauth2Error.getDescription() != null) {
                errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
            }
            errorDetails.append("]");
            oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails,
                null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        } catch (UnknownContentTypeException ex) {
            String errorMessage = "An error occurred while attempting to retrieve the UserInfo Resource from '"
                + userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri()
                + "': response contains invalid content type '" + ex.getContentType().toString() + "'. "
                + "The UserInfo Response should return a JSON object (content type 'application/json') "
                + "that contains a collection of name and value pairs of the claims about the authenticated End-User. "
                + "Please ensure the UserInfo Uri in UserInfoEndpoint for Client Registration '"
                + registrationId + "' conforms to the UserInfo Endpoint, "
                + "as defined in OpenID Connect 1.0: 'https://openid.net/specs/openid-connect-core-1_0.html#UserInfo'";
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE, errorMessage, null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        } catch (RestClientException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
        }
    }

    /**
     * Sets the {@link Converter} used for converting the {@link OAuth2UserRequest} to a
     * {@link RequestEntity} representation of the UserInfo Request.
     *
     * @param requestEntityConverter the {@link Converter} used for converting to a
     *                               {@link RequestEntity} representation of the UserInfo Request
     * @since 5.1
     */
    public final void setRequestEntityConverter(Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter) {
        Assert.notNull(requestEntityConverter, "requestEntityConverter cannot be null");
        this.requestEntityConverter = requestEntityConverter;
    }

}
