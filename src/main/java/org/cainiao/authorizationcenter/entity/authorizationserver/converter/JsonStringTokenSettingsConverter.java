package org.cainiao.authorizationcenter.entity.authorizationserver.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
@RequiredArgsConstructor
public class JsonStringTokenSettingsConverter
    implements AttributeConverter<TokenSettings, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(TokenSettings attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute.getSettings());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting TokenSettings to JSON string", e);
        }
    }

    @Override
    public TokenSettings convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return TokenSettings.builder().build();
        }
        try {
            Map<String, Object> settings = objectMapper.readValue(dbData, new TypeReference<>() {
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
            throw new RuntimeException("Error converting JSON string to TokenSettings", e);
        }
    }
}
