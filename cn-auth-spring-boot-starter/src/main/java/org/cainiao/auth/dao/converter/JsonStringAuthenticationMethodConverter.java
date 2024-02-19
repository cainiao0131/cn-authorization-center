package org.cainiao.auth.dao.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.cainiao.auth.util.OAuth2Util;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.util.StringUtils;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Converter
public class JsonStringAuthenticationMethodConverter implements AttributeConverter<AuthenticationMethod, String> {

    @Override
    public String convertToDatabaseColumn(AuthenticationMethod attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public AuthenticationMethod convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? OAuth2Util.resolveAuthenticationMethod(dbData) : null;
    }
}
