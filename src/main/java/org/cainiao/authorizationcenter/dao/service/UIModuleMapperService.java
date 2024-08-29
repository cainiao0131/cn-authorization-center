package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.UIModuleMapper;
import org.cainiao.authorizationcenter.entity.acl.UIModule;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class UIModuleMapperService extends ServiceImpl<UIModuleMapper, UIModule> implements IService<UIModule> {

    public UIModule findByApplicationIdAndKey(long appId, String key) {
        return lambdaQuery().eq(UIModule::getApplicationId, appId).eq(UIModule::getKey, key).one();
    }
}
