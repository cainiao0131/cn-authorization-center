package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.ApplicationMapper;
import org.cainiao.authorizationcenter.entity.acl.technology.Application;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class ApplicationMapperService
    extends ServiceImpl<ApplicationMapper, Application> implements IService<Application> {

}
