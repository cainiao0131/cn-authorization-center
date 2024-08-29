package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.LarkUserMapper;
import org.cainiao.authorizationcenter.entity.acl.LarkUser;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class LarkUserMapperService extends ServiceImpl<LarkUserMapper, LarkUser> implements IService<LarkUser> {

    public LarkUser findByLarkUserId(String larkUserId) {
        return lambdaQuery().eq(LarkUser::getLarkUserId, larkUserId).one();
    }
}
