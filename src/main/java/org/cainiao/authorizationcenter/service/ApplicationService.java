package org.cainiao.authorizationcenter.service;

import org.cainiao.authorizationcenter.entity.acl.technology.Application;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface ApplicationService {

    Application findByClientId(String clientId);
}
