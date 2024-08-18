package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@MappedTypes({ClientSettings.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ClientSettingsTypeHandler extends BaseTypeHandler<ClientSettings> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ClientSettings parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setObject(i, JsonUtil.toJsonString(parameter.getSettings()));
        } catch (JsonProcessingException e) {
            throw new BusinessException("Error converting ClientSettings to JSON string", e);
        }
    }

    @Override
    public ClientSettings getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public ClientSettings getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public ClientSettings getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private ClientSettings parseJson(String jsonString) {
        if (!StringUtils.hasText(jsonString)) {
            return ClientSettings.builder().build();
        }
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
}
