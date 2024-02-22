package org.cainiao.authorizationcenter.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
@RequiredArgsConstructor
public class JsonStringTokenSettingsConverter
    implements AttributeConverter<TokenSettings, String>
{

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
            Map<String, Object> settings = objectMapper.readValue(dbData, new TypeReference<>() {});
            if (settings.isEmpty()) {
                return TokenSettings.builder().build();
            }
            return TokenSettings.withSettings(settings).build();
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to TokenSettings", e);
        }
    }
}
