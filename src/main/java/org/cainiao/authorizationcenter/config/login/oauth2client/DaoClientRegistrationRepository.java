package org.cainiao.authorizationcenter.config.login.oauth2client;

import lombok.RequiredArgsConstructor;
import org.cainiao.oauth2.client.core.dao.repository.JpaClientRegistrationRepository;
import org.cainiao.oauth2.client.core.entity.JpaClientRegistration;
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

    private final JpaClientRegistrationRepository jpaClientRegistrationRepository;

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        Assert.hasText(registrationId, "registrationId cannot be empty");
        return jpaClientRegistrationRepository.findByRegistrationId(registrationId)
            .map(JpaClientRegistration::toRegisteredClient).orElse(null);
    }

    /**
     * Returns an {@code Iterator} of {@link ClientRegistration}.
     * @return an {@code Iterator<ClientRegistration>}
     */
    @Override
    public Iterator<ClientRegistration> iterator() {
        return jpaClientRegistrationRepository.findAll().stream()
            .map(JpaClientRegistration::toRegisteredClient).iterator();
    }
}
