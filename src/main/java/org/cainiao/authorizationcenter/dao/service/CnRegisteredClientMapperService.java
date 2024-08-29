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
        return lambdaQuery().eq(CnRegisteredClient::getRegisteredClientId, registeredClientId).one();
    }

    public CnRegisteredClient findByClientId(String clientId) {
        return lambdaQuery().eq(CnRegisteredClient::getClientId, clientId).one();
    }

    public boolean existsByClientId(String clientId) {
        return lambdaQuery().eq(CnRegisteredClient::getClientId, clientId).exists();
    }

    public boolean existsByClientSecret(String clientSecret) {
        return lambdaQuery().eq(CnRegisteredClient::getClientSecret, clientSecret).exists();
    }
}
