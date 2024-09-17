package org.cainiao.authorizationcenter.config.login.oauth2client;

import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.service.CnOAuth2AuthorizedClientMapperService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
@RequiredArgsConstructor
public class DaoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final CnOAuth2AuthorizedClientMapperService cnOAuth2AuthorizedClientMapperService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
                                                                     String principalName) {
        @SuppressWarnings("unchecked")
        T authorizedClient = (T) cnOAuth2AuthorizedClientMapperService
            .findByRegistrationIdAndPrincipalName(clientRegistrationId, principalName)
            .toOAuth2AuthorizedClient(clientRegistrationRepository);
        return authorizedClient;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        cnOAuth2AuthorizedClientMapperService.save(authorizedClient);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        cnOAuth2AuthorizedClientMapperService.remove(clientRegistrationId, principalName);
    }
}
