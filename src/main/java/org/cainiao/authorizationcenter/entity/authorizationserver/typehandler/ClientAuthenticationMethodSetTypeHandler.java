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
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
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
public class ClientAuthenticationMethodSetTypeHandler extends BaseTypeHandler<Set<ClientAuthenticationMethod>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<ClientAuthenticationMethod> parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, getJson(parameter));
    }

    private String getJson(Set<ClientAuthenticationMethod> parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return "[]";
        }
        try {
            return JsonUtil.toJsonString(parameter.stream()
                .map(ClientAuthenticationMethod::getValue).collect(Collectors.toSet()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Set<ClientAuthenticationMethod> to JSON string", e);
        }
    }

    @Override
    public Set<ClientAuthenticationMethod> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public Set<ClientAuthenticationMethod> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public Set<ClientAuthenticationMethod> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private Set<ClientAuthenticationMethod> parseJson(String jsonString) {
        if (!StringUtils.hasText(jsonString)) {
            return new HashSet<>();
        }
        try {
            return JsonUtil.parseJson(jsonString, new TypeReference<Set<String>>() {
                }).stream()
                .map(OAuth2Util::resolveClientAuthenticationMethod).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new BusinessException("Error converting JSON string to Set<ClientAuthenticationMethod>", e);
        }
    }
}
