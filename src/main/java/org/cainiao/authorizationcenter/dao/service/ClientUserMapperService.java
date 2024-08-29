package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.ClientUserMapper;
import org.cainiao.authorizationcenter.entity.authorizationserver.ClientUser;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class ClientUserMapperService extends ServiceImpl<ClientUserMapper, ClientUser> implements IService<ClientUser> {

    public ClientUser findByUserIdAndClientId(long userId, String clientId) {
        return lambdaQuery().eq(ClientUser::getUserId, userId).eq(ClientUser::getClientId, clientId).one();
    }
}
