package org.cainiao.authorizationcenter.config.login.oauth2client;

import org.cainiao.authorizationcenter.dao.service.CnOAuth2AuthorizedClientMapperService;
import org.cainiao.authorizationcenter.entity.oauth2login.CnOAuth2AuthorizedClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
public class DaoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final CnOAuth2AuthorizedClientMapperService cnOAuth2AuthorizedClientMapperService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
                                                                     String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (clientRegistration == null) {
            return null;
        }

        CnOAuth2AuthorizedClient cnOAuth2AuthorizedClient = cnOAuth2AuthorizedClientMapperService
            .findByRegistrationIdAndPrincipalName(clientRegistrationId, principalName);
        if (cnOAuth2AuthorizedClient == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T authorizedClient = (T) cnOAuth2AuthorizedClient.toOAuth2AuthorizedClient(clientRegistration);
        return authorizedClient;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");

        cnOAuth2AuthorizedClientMapperService.saveOrUpdate(authorizedClient, principal.getName());
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");

        cnOAuth2AuthorizedClientMapperService.remove(clientRegistrationId, principalName);
    }

    public DaoOAuth2AuthorizedClientService(
        CnOAuth2AuthorizedClientMapperService cnOAuth2AuthorizedClientMapperService,
        ClientRegistrationRepository clientRegistrationRepository) {

        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");

        this.cnOAuth2AuthorizedClientMapperService = cnOAuth2AuthorizedClientMapperService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
}
