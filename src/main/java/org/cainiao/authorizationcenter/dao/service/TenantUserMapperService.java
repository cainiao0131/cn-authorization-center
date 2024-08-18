package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.TenantUserMapper;
import org.cainiao.authorizationcenter.entity.acl.organization.TenantUser;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class TenantUserMapperService
    extends ServiceImpl<TenantUserMapper, TenantUser> implements IService<TenantUser> {

    public boolean existsByTenantIdAndUserId(long tenantId, long userId) {
        return count(lambdaQuery().eq(TenantUser::getTenantId, tenantId).eq(TenantUser::getUserId, userId)) > 0;
    }
}
