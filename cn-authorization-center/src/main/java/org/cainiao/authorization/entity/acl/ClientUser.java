package org.cainiao.authorization.entity.acl;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;

import java.io.Serial;

/**
 * 应用用户<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "t_client_user")
@Schema(name = "ClientUser", description = "应用用户")
public class ClientUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -8422207240504678235L;

    @Column(name = "au_client_id", length = 32)
    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    private String clientId;

    @Column(name = "au_user_name", length = 32)
    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    private String userName;

}
