package org.cainiao.authorizationcenter.entity.authorizationserver.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
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
public class JsonStringClientSettingsConverter
    implements AttributeConverter<ClientSettings, String>
{

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(ClientSettings attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute.getSettings());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting ClientSettings to JSON string", e);
        }
    }

    @Override
    public ClientSettings convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return ClientSettings.builder().build();
        }
        try {
            Map<String, Object> settings = objectMapper.readValue(dbData, new TypeReference<>() {});
            if (settings.isEmpty()) {
                return ClientSettings.builder().build();
            }
            return ClientSettings.withSettings(settings).build();
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to ClientSettings", e);
        }
    }
}
