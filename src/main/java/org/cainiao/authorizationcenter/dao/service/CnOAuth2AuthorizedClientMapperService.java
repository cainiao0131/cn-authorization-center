package org.cainiao.authorizationcenter.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cainiao.authorizationcenter.dao.mapper.CnOAuth2AuthorizedClientMapper;
import org.cainiao.authorizationcenter.entity.oauth2login.CnOAuth2AuthorizedClient;
import org.cainiao.common.exception.BusinessException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveOrUpdate(OAuth2AuthorizedClient authorizedClient, String principalName) {
        CnOAuth2AuthorizedClient cnOAuth2AuthorizedClient = CnOAuth2AuthorizedClient
            .from(authorizedClient, principalName);
        CnOAuth2AuthorizedClient existCnOAuth2AuthorizedClient = findByRegistrationIdAndPrincipalName(authorizedClient
            .getClientRegistration().getRegistrationId(), principalName);
        if (existCnOAuth2AuthorizedClient == null) {
            if (!save(cnOAuth2AuthorizedClient)) {
                throw new BusinessException("save CnOAuth2AuthorizedClient fail");
            }
        } else {
            cnOAuth2AuthorizedClient.setId(existCnOAuth2AuthorizedClient.getId());
            cnOAuth2AuthorizedClient.setCreatedAt(existCnOAuth2AuthorizedClient.getCreatedAt());
            cnOAuth2AuthorizedClient.setUpdatedAt(LocalDateTime.now());
            if (!updateById(cnOAuth2AuthorizedClient)) {
                throw new BusinessException("updateById CnOAuth2AuthorizedClient fail");
            }
        }
    }

    public boolean remove(String registrationId, String principalName) {
        return remove(lambdaQuery().eq(CnOAuth2AuthorizedClient::getRegistrationId, registrationId)
            .eq(CnOAuth2AuthorizedClient::getPrincipalName, principalName));
    }
}
