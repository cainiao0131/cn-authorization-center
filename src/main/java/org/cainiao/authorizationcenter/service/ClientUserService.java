package org.cainiao.authorizationcenter.service;

import org.cainiao.authorizationcenter.entity.authorizationserver.ClientUser;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface ClientUserService {

    /**
     * 根据平台用户 ID 与 OAuth2 客户端 ID<br />
     * 返回 ClientUser，如果没有，则生成 openId 并插入记录
     *
     * @param userId   平台用户 ID
     * @param clientId OAuth2 客户端 ID
     * @return ClientUser
     */
    ClientUser createIfFirstUse(long userId, String clientId);
}
