package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@MappedTypes({TokenSettings.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TokenSettingsTypeHandler extends BaseTypeHandler<TokenSettings> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TokenSettings parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, getJson(parameter));
    }

    private String getJson(TokenSettings parameter) {
        if (parameter == null) {
            return null;
        }
        try {
            return JsonUtil.toJsonString(parameter.getSettings());
        } catch (JsonProcessingException e) {
            throw new BusinessException("Error converting TokenSettings to JSON string", e);
        }
    }

    @Override
    public TokenSettings getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public TokenSettings getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public TokenSettings getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private TokenSettings parseJson(String jsonString) {
        if (!StringUtils.hasText(jsonString)) {
            return TokenSettings.builder().build();
        }
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
}
