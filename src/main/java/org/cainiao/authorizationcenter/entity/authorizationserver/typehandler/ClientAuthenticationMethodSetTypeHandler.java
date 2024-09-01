package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.oauth2.client.util.OAuth2Util;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class ClientAuthenticationMethodSetTypeHandler extends AbstractJsonTypeHandler<Set<ClientAuthenticationMethod>> {

    public ClientAuthenticationMethodSetTypeHandler() {
        super(Set.class);
    }

    @Override
    public Set<ClientAuthenticationMethod> parse(String jsonString) {
        try {
            return JsonUtil.parseJson(jsonString, new TypeReference<Set<String>>() {
                }).stream()
                .map(OAuth2Util::resolveClientAuthenticationMethod).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new BusinessException("Error converting JSON string to Set<ClientAuthenticationMethod>", e);
        }
    }

    @Override
    public String toJson(Set<ClientAuthenticationMethod> clientAuthenticationMethodSet) {
        if (clientAuthenticationMethodSet == null || clientAuthenticationMethodSet.isEmpty()) {
            return "[]";
        }
        try {
            return JsonUtil.toJsonString(clientAuthenticationMethodSet.stream()
                .map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Set<ClientAuthenticationMethod> to JSON string", e);
        }
    }
}
