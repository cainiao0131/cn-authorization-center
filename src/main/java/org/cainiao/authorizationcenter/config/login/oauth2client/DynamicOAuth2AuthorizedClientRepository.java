package org.cainiao.authorizationcenter.config.login.oauth2client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collection;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Component
public class DynamicOAuth2AuthorizedClientRepository implements OAuth2AuthorizedClientRepository {

    // TODO 改为可配置
    public static final String SHARED_PRINCIPAL_NAME = "1";
    private static final Authentication SHARED_PRINCIPAL = new Authentication() {

        @Serial
        private static final long serialVersionUID = 504035359716428222L;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return SHARED_PRINCIPAL_NAME;
        }
    };
    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    private final OAuth2AuthorizedClientService authorizedClientService;

    /**
     * Constructs a {@code AuthenticatedPrincipalOAuth2AuthorizedClientRepository} using
     * the provided parameters.
     *
     * @param authorizedClientService the authorized client service
     */
    public DynamicOAuth2AuthorizedClientRepository(
        OAuth2AuthorizedClientService authorizedClientService) {
        Assert.notNull(authorizedClientService, "authorizedClientService cannot be null");
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
                                                                     Authentication principal,
                                                                     HttpServletRequest request) {
        return authorizedClientService.loadAuthorizedClient(clientRegistrationId, getPrincipalName(principal));
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal,
                                     HttpServletRequest request, HttpServletResponse response) {
        authorizedClientService.saveAuthorizedClient(authorizedClient,
            isPrincipalAuthenticated(principal) ? principal : SHARED_PRINCIPAL);
    }

    /**
     * TODO 当某些环节，例如共享用户的登出导致共享 access token 被删除的情况可能导致 bug
     *  目前是靠共享用户不登出来保证不出问题的
     *  但这个自定义的 OAuth2AuthorizedClientRepository 让匿名与非匿名用户共同使用 OAuth2AuthorizedClientService 可能存在问题
     *  是否有更健壮的方式？
     */
    @Override
    public void removeAuthorizedClient(String clientRegistrationId, Authentication principal,
                                       HttpServletRequest request, HttpServletResponse response) {
        authorizedClientService.removeAuthorizedClient(clientRegistrationId, getPrincipalName(principal));
    }

    private String getPrincipalName(Authentication principal) {
        return isPrincipalAuthenticated(principal) ? principal.getName() : SHARED_PRINCIPAL_NAME;
    }

    private boolean isPrincipalAuthenticated(Authentication authentication) {
        return authentication != null && !this.authenticationTrustResolver.isAnonymous(authentication)
            && authentication.isAuthenticated();
    }
}