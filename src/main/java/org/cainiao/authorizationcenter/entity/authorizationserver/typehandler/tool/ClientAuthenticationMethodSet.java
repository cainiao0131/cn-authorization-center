package org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.tool;

import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
public class ClientAuthenticationMethodSet extends HashSet<ClientAuthenticationMethod> {

    @Serial
    private static final long serialVersionUID = 439787700582000059L;

    public static ClientAuthenticationMethodSet from(Set<ClientAuthenticationMethod> clientAuthenticationMethods) {
        if (clientAuthenticationMethods == null) {
            return null;
        }
        ClientAuthenticationMethodSet clientAuthenticationMethodSet = new ClientAuthenticationMethodSet();
        clientAuthenticationMethodSet.addAll(clientAuthenticationMethods);
        return clientAuthenticationMethodSet;
    }
}
