package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import org.cainiao.common.exception.BusinessException;
import org.cainiao.oauth2.client.core.util.JsonUtil;

import java.util.Set;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class SetTypeHandler extends AbstractJsonTypeHandler<Set<String>> {

    public SetTypeHandler() {
        super(Set.class);
    }

    @Override
    public Set<String> parse(String jsonString) {
        try {
            return JsonUtil.parseJson(jsonString, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new BusinessException("Failed to convert JSON to Set<String>", e);
        }
    }

    @Override
    public String toJson(Set<String> set) {
        try {
            return JsonUtil.toJsonString(set);
        } catch (Exception e) {
            throw new BusinessException("Failed to convert Set<String> to JSON", e);
        }
    }
}
