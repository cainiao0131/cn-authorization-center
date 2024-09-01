package org.cainiao.authorizationcenter.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorizationcenter.entity.authorizationserver.CnRegisteredClient;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "OAuth2Client", description = "OAuth2 客户端")
public class OAuth2Client extends CnRegisteredClient {

    @Serial
    private static final long serialVersionUID = -7754688129198568662L;

    @Schema(hidden = true)
    @JsonIgnore
    private long tenantId;

    @Schema(hidden = true)
    @JsonIgnore
    private String registeredClientId;

    @Schema(hidden = true)
    @JsonIgnore
    private Long id;

    @Schema(hidden = true)
    @JsonIgnore
    private String createdBy;

    @Schema(hidden = true)
    @JsonIgnore
    @Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Schema(hidden = true)
    @JsonIgnore
    private String updatedBy;

    @Schema(hidden = true)
    @JsonIgnore
    @Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
