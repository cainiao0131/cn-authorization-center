package org.cainiao.authorizationcenter.config.login.oauth2client.httpclient.lark;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class LarkMapOAuth2AccessTokenResponseConverter
    implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    @Override
    public OAuth2AccessTokenResponse convert(@NonNull Map<String, Object> source) {
        Object code = source.get("code");
        if (!"0".equals(code.toString())) {
            throw new OAuth2AuthorizationException(
                new OAuth2Error(code.toString(), source.get("message").toString(), null));
        }
        Object data = source.get("data");
        if (!(data instanceof Map<?, ?> dataMap)) {
            throw new OAuth2AuthorizationException(new OAuth2Error(code.toString(), "no data in response", null));
        }
        return OAuth2AccessTokenResponse.withToken(getParameterValue(dataMap, OAuth2ParameterNames.ACCESS_TOKEN))
            .tokenType(getAccessTokenType(dataMap))
            .expiresIn(getExpiresIn(dataMap))
            .scopes(getScopes(dataMap))
            .refreshToken(getParameterValue(dataMap, OAuth2ParameterNames.REFRESH_TOKEN))
            .additionalParameters(new LinkedHashMap<>())
            .build();
    }

    private static TokenType getAccessTokenType(Map<?, ?> tokenResponseParameters) {
        if (TokenType.BEARER.getValue()
            .equalsIgnoreCase(getParameterValue(tokenResponseParameters, OAuth2ParameterNames.TOKEN_TYPE))) {
            return TokenType.BEARER;
        }
        return null;
    }

    private static long getExpiresIn(Map<?, ?> tokenResponseParameters) {
        long parameterValue = 0L;
        Object obj = tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN);
        if (obj != null) {
            // Final classes Long and Integer do not need to be coerced
            if (obj.getClass() == Long.class) {
                parameterValue = (Long) obj;
            } else if (obj.getClass() == Integer.class) {
                parameterValue = (Integer) obj;
            } else {
                // Attempt to coerce to a long (typically from a String)
                try {
                    parameterValue = Long.parseLong(obj.toString());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return parameterValue;
    }

    private static Set<String> getScopes(Map<?, ?> tokenResponseParameters) {
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            String scope = getParameterValue(tokenResponseParameters, OAuth2ParameterNames.SCOPE);
            return new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }
        return Collections.emptySet();
    }

    private static String getParameterValue(Map<?, ?> tokenResponseParameters, String parameterName) {
        Object obj = tokenResponseParameters.get(parameterName);
        return obj != null ? obj.toString() : null;
    }

}
