package org.cainiao.authorizationcenter.config.login.oauth2client;

import lombok.RequiredArgsConstructor;
import org.cainiao.oauth2.client.core.dao.service.CnClientRegistrationMapperService;
import org.cainiao.oauth2.client.core.entity.CnClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class DaoClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration> {

    private final CnClientRegistrationMapperService cnClientRegistrationMapperService;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        Assert.hasText(registrationId, "registrationId cannot be empty");
        CnClientRegistration cnClientRegistration = cnClientRegistrationMapperService
            .findByRegistrationId(registrationId);
        return cnClientRegistration == null ? null : cnClientRegistration.toRegisteredClient();
    }

    /**
     * Returns an {@code Iterator} of {@link ClientRegistration}.
     *
     * @return an {@code Iterator<ClientRegistration>}
     */
    @Override
    public Iterator<ClientRegistration> iterator() {
        return cnClientRegistrationMapperService.list()
            .stream().map(CnClientRegistration::toRegisteredClient).iterator();
    }
}
