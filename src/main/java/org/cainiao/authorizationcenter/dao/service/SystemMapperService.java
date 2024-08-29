package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.SystemMapper;
import org.cainiao.authorizationcenter.entity.acl.technology.System;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class SystemMapperService extends ServiceImpl<SystemMapper, System> implements IService<System> {

    public System findById(long id) {
        return lambdaQuery().eq(System::getId, id).one();
    }

    public System findByClientId(String clientId) {
        return lambdaQuery().eq(System::getClientId, clientId).one();
    }
}
