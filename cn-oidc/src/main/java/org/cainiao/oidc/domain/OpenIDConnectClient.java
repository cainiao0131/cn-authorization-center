package org.cainiao.oidc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OpenIDConnectClient {

    @JsonProperty("issuer")
    String issuer;

    @JsonProperty("authorization_endpoint")
    String authorizationEndpoint;

    @JsonProperty("device_authorization_endpoint")
    String deviceAuthorizationEndpoint;

    @JsonProperty("token_endpoint")
    String tokenEndpoint;

    @JsonProperty("token_endpoint_auth_methods_supported")
    List<String> tokenEndpointAuthMethodsSupported;

    @JsonProperty("jwks_uri")
    String jwksUri;

    @JsonProperty("userinfo_endpoint")
    String userinfoEndpoint;

    @JsonProperty("end_session_endpoint")
    String endSessionEndpoint;

    @JsonProperty("response_types_supported")
    List<String> responseTypesSupported;

    @JsonProperty("grant_types_supported")
    List<String> grantTypesSupported;

    @JsonProperty("revocation_endpoint")
    String revocationEndpoint;

    @JsonProperty("revocation_endpoint_auth_methods_supported")
    List<String> revocationEndpointAuthMethodsSupported;

    @JsonProperty("introspection_endpoint")
    String introspectionEndpoint;

    @JsonProperty("introspection_endpoint_auth_methods_supported")
    List<String> introspectionEndpointAuthMethodsSupported;

    @JsonProperty("subject_types_supported")
    List<String> subjectTypesSupported;

    @JsonProperty("id_token_signing_alg_values_supported")
    List<String> idTokenSigningAlgValuesSupported;

    @JsonProperty("scopes_supported")
    List<String> scopesSupported;

}
