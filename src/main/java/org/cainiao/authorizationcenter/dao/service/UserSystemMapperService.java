package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.UserSystemMapper;
import org.cainiao.authorizationcenter.entity.acl.technology.UserSystem;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class UserSystemMapperService extends ServiceImpl<UserSystemMapper, UserSystem> implements IService<UserSystem> {

    public boolean existsByUserIdAndSystemId(long userId, long systemId) {
        return lambdaQuery().eq(UserSystem::getUserId, userId).eq(UserSystem::getSystemId, systemId).exists();
    }
}
