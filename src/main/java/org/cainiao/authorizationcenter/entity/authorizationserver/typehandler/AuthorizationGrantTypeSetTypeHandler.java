package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.tool.AuthorizationGrantTypeSet;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.oauth2.client.util.OAuth2Util;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class AuthorizationGrantTypeSetTypeHandler extends AbstractJsonTypeHandler<AuthorizationGrantTypeSet> {

    public AuthorizationGrantTypeSetTypeHandler() {
        super(AuthorizationGrantTypeSet.class);
    }

    @Override
    public AuthorizationGrantTypeSet parse(String jsonString) {
        try {
            AuthorizationGrantTypeSet authorizationGrantTypeSet = new AuthorizationGrantTypeSet();
            authorizationGrantTypeSet.addAll(JsonUtil.parseJson(jsonString, new TypeReference<Set<String>>() {
                }).stream()
                .map(OAuth2Util::resolveAuthorizationGrantType).collect(Collectors.toSet()));
            return authorizationGrantTypeSet;
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to Set<AuthorizationGrantType>", e);
        }
    }

    @Override
    public String toJson(AuthorizationGrantTypeSet authorizationGrantTypeSet) {
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
