package org.cainiao.authorizationcenter.service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public interface TenantUserService {

    /**
     * 如果用户是第一次使用某【租户】下的【系统】，则为用户创建在这个【租户】下的唯一标识，即【租户用户 ID】
     *
     * @param userId   平台用户 ID
     * @param systemId 系统 ID
     */
    void createIfFirstUse(long userId, long systemId);
}
