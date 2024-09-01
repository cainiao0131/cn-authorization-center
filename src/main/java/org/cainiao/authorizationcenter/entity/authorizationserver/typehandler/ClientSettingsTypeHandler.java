package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.io.IOException;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class ClientSettingsTypeHandler extends AbstractJsonTypeHandler<ClientSettings> {

    public ClientSettingsTypeHandler() {
        super(ClientSettings.class);
    }

    @Override
    public ClientSettings parse(String jsonString) {
        try {
            Map<String, Object> settings = JsonUtil.parseJson(jsonString, new TypeReference<>() {
            });
            if (settings.isEmpty()) {
                return ClientSettings.builder().build();
            }
            return ClientSettings.withSettings(settings).build();
        } catch (IOException e) {
            throw new BusinessException("Error converting JSON string to ClientSettings", e);
        }
    }

    @Override
    public String toJson(ClientSettings clientSettings) {
        try {
            return JsonUtil.toJsonString(clientSettings.getSettings());
        } catch (JsonProcessingException e) {
            throw new BusinessException("Error converting ClientSettings to JSON string", e);
        }
    }
}
