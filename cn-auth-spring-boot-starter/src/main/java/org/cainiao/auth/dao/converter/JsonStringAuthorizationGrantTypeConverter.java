package org.cainiao.auth.dao.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.cainiao.auth.util.OAuth2Util;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.StringUtils;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Converter
public class JsonStringAuthorizationGrantTypeConverter implements AttributeConverter<AuthorizationGrantType, String> {

    @Override
    public String convertToDatabaseColumn(AuthorizationGrantType attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public AuthorizationGrantType convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? OAuth2Util.resolveAuthorizationGrantType(dbData) : null;
    }
}
