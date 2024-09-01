package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.tool;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class AuthorizationGrantTypeSet extends HashSet<AuthorizationGrantType> {

    @Serial
    private static final long serialVersionUID = 2914007960972021574L;

    public static AuthorizationGrantTypeSet from(Set<AuthorizationGrantType> authorizationGrantTypes) {
        if (authorizationGrantTypes == null) {
            return null;
        }
        AuthorizationGrantTypeSet authorizationGrantTypeSet = new AuthorizationGrantTypeSet();
        authorizationGrantTypeSet.addAll(authorizationGrantTypes);
        return authorizationGrantTypeSet;
    }
}
