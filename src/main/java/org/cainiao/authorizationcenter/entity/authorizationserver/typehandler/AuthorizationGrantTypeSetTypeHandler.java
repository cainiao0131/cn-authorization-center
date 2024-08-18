package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.cainiao.authorizationcenter.util.JsonUtil;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.oauth2.client.util.OAuth2Util;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@MappedTypes({Set.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class AuthorizationGrantTypeSetTypeHandler extends BaseTypeHandler<Set<AuthorizationGrantType>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Set<AuthorizationGrantType> parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, getJson(parameter));
    }

    private String getJson(Set<AuthorizationGrantType> parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return "[]";
        }
        try {
            return JsonUtil.toJsonString(parameter.stream()
                .map(AuthorizationGrantType::getValue).collect(Collectors.toSet()));
        } catch (JsonProcessingException e) {
            throw new BusinessException("Error converting Set<AuthorizationGrantType> to JSON string", e);
        }
    }

    @Override
    public Set<AuthorizationGrantType> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public Set<AuthorizationGrantType> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public Set<AuthorizationGrantType> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private Set<AuthorizationGrantType> parseJson(String jsonString) {
        if (!StringUtils.hasText(jsonString)) {
            return new HashSet<>();
        }
        try {
            return JsonUtil.parseJson(jsonString, new TypeReference<Set<String>>() {
                }).stream()
                .map(OAuth2Util::resolveAuthorizationGrantType).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to Set<AuthorizationGrantType>", e);
        }
    }
}
