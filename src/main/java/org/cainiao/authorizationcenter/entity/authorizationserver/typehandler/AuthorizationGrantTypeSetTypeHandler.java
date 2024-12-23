package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.oauth2.client.util.OAuth2Util;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class AuthorizationGrantTypeSetTypeHandler extends AbstractJsonTypeHandler<Set<AuthorizationGrantType>> {

    public AuthorizationGrantTypeSetTypeHandler(Class<?> type) {
        super(type);
    }

    public AuthorizationGrantTypeSetTypeHandler(Class<?> type, Field field) {
        super(type, field);
    }

    @Override
    public Set<AuthorizationGrantType> parse(String jsonString) {
        if (!StringUtils.hasText(jsonString)) {
            return new HashSet<>();
        }
        try {
            return JsonUtil.parseJson(jsonString, new TypeReference<Set<String>>() {
                }).stream()
                .map(OAuth2Util::resolveAuthorizationGrantType).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to Set<AuthorizationGrantType>", e);
        }
    }

    @Override
    public String toJson(Set<AuthorizationGrantType> authorizationGrantTypeSet) {
        if (authorizationGrantTypeSet == null || authorizationGrantTypeSet.isEmpty()) {
            return "[]";
        }
        try {
            return JsonUtil.toJsonString(authorizationGrantTypeSet.stream()
                .map(AuthorizationGrantType::getValue).collect(Collectors.toSet()));
        } catch (JsonProcessingException e) {
            throw new BusinessException("Error converting Set<AuthorizationGrantType> to JSON string", e);
        }
    }
}
