package org.cainiao.authorizationcenter.entity.authorizationserver.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.cainiao.oauth2.client.util.OAuth2Util;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
@RequiredArgsConstructor
public class JsonStringClientAuthenticationMethodSetConverter
    implements AttributeConverter<Set<ClientAuthenticationMethod>, String>
{

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Set<ClientAuthenticationMethod> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(attribute.stream()
                .map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Set<ClientAuthenticationMethod> to JSON string", e);
        }
    }

    @Override
    public Set<ClientAuthenticationMethod> convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return new HashSet<>();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<Set<String>>() {}).stream()
                .map(OAuth2Util::resolveClientAuthenticationMethod).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to Set<ClientAuthenticationMethod>", e);
        }
    }
}
