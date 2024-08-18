package org.cainiao.authorizationcenter.service;

import org.cainiao.authorizationcenter.entity.acl.technology.ClientUser;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface SystemUserService {

    /**
     * 根据平台用户 ID 与 OAuth2 客户端 ID<br />
     * 查询这个 OAuth2 客户端 ID 对应的【环境应用】，找到对应的【应用】所属【系统】，返回【系统用户】<br />
     * 如果这是这个用户第一次使用这个【系统】的【应用】，则为用户创建在这个【系统】下的唯一标识，即【系统用户 ID】
     *
     * @param userId             平台用户 ID
     * @param registeredClientId OAuth2 客户端 ID
     * @return 系统用户
     */
    ClientUser createIfFirstUse(long userId, String clientId);
}
