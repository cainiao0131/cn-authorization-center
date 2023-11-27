package org.cainiao.authorization.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.oauth2.RegisteredClient;

import java.io.Serial;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@SuperBuilder
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Schema(name = "RegisteredClientInfo", description = "认证客户端完整信息")
public class RegisteredClientInfo extends RegisteredClient {

    @Serial
    private static final long serialVersionUID = -6326873886796058683L;

}
