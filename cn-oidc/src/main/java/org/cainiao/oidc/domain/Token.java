package org.cainiao.oidc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("refresh_token")
    String refreshToken;

    String scope;

    @JsonProperty("id_token")
    String idToken;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("expires_in")
    int expiresIn;

}
