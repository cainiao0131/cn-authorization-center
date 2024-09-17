package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.CnOAuth2AuthorizedClientMapper;
import org.cainiao.authorizationcenter.entity.oauth2login.CnOAuth2AuthorizedClient;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
public class CnOAuth2AuthorizedClientMapperService
    extends ServiceImpl<CnOAuth2AuthorizedClientMapper, CnOAuth2AuthorizedClient>
    implements IService<CnOAuth2AuthorizedClient> {

    public CnOAuth2AuthorizedClient findByRegistrationIdAndPrincipalName(String registrationId,
                                                                         String principalName) {
        return lambdaQuery().eq(CnOAuth2AuthorizedClient::getRegistrationId, registrationId)
            .eq(CnOAuth2AuthorizedClient::getPrincipalName, principalName).one();
    }

    public void save(OAuth2AuthorizedClient authorizedClient) {
        if (!save(CnOAuth2AuthorizedClient.from(authorizedClient))) {
            throw new BusinessException("save CnOAuth2AuthorizedClient fail");
        }
    }

    public void remove(String registrationId, String principalName) {
        if (!remove(lambdaQuery().eq(CnOAuth2AuthorizedClient::getRegistrationId, registrationId)
            .eq(CnOAuth2AuthorizedClient::getPrincipalName, principalName))) {

            throw new BusinessException("remove fail, registrationId = s%, principalName = %s",
                registrationId, principalName);
        }
    }
}
