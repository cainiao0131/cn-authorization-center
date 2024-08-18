package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.CnRegisteredClientMapper;
import org.cainiao.authorizationcenter.entity.authorizationserver.CnRegisteredClient;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class CnRegisteredClientMapperService
    extends ServiceImpl<CnRegisteredClientMapper, CnRegisteredClient> implements IService<CnRegisteredClient> {

    public CnRegisteredClient findByRegisteredClientId(String registeredClientId) {
        return getOne(lambdaQuery().eq(CnRegisteredClient::getRegisteredClientId, registeredClientId));
    }

    public CnRegisteredClient findByClientId(String clientId) {
        return getOne(lambdaQuery().eq(CnRegisteredClient::getClientId, clientId));
    }

    public boolean existsByClientId(String clientId) {
        return count(lambdaQuery().eq(CnRegisteredClient::getClientId, clientId)) > 0;
    }

    public boolean existsByClientSecret(String clientSecret) {
        return count(lambdaQuery().eq(CnRegisteredClient::getClientSecret, clientSecret)) > 0;
    }
}
