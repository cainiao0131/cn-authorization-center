package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class TokenSettingsTypeHandler extends AbstractJsonTypeHandler<TokenSettings> {


    public TokenSettingsTypeHandler() {
        super(TokenSettings.class);
    }

    @Override
    public TokenSettings parse(String jsonString) {
        try {
            Map<String, Object> settings = JsonUtil.parseJson(jsonString, new TypeReference<>() {
            });
            if (settings.isEmpty()) {
                return TokenSettings.builder().build();
            }
            // 处理 Duration，因为 readValue 的类型为 Map<String, Object>，所以解码器没有触发，仍然是 String 类型的
            settings.entrySet().forEach(entry -> {
                switch (entry.getKey()) {
                    case "settings.token.access-token-time-to-live", "settings.token.refresh-token-time-to-live",
                        "settings.token.authorization-code-time-to-live", "settings.token.device-code-time-to-live" -> {

                        try {
                            entry.setValue(Duration.parse(entry.getValue().toString()));
                        } catch (Exception ignored) {
                        }
                    }
                }
            });
            return TokenSettings.withSettings(settings).build();
        } catch (IOException e) {
            throw new BusinessException("Error converting JSON string to TokenSettings", e);
        }
    }

    @Override
    public String toJson(TokenSettings tokenSettings) {
        if (tokenSettings == null) {
            return null;
        }
        try {
            return JsonUtil.toJsonString(tokenSettings.getSettings());
        } catch (JsonProcessingException e) {
            throw new BusinessException("Error converting TokenSettings to JSON string", e);
        }
    }
}
