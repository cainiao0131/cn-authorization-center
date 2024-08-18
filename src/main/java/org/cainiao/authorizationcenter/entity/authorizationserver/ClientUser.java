package org.cainiao.authorizationcenter.entity.authorizationserver;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * OAuth2 客户端被哪些用户使用过
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_client_user")
@Schema(name = "ClientUser", description = "OAuth2 客户端用户")
public class ClientUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4017908273219433506L;

    @TableField("cu_client_id")
    @Schema(description = "OAuth2 客户端 ID")
    private long clientId;

    @TableField("cu_user_id")
    @Schema(description = "平台用户 ID")
    private long userId;

    /**
     * 用户在这个 OAuth2 客户端中的唯一标识<br />
     * 等价于 t_client_user 表的 ID，用于做业务<br />
     * 用户第一次通过 OAuth2 登录该客户端时创建<br />
     * 系统内的资源应该以这个 openId 来标识资源由哪个用户所有，而不是用平台用户ID<br />
     * 以便可以让用户ID在系统间隔离（类似飞书的open_id）
     */
    @TableField("cu_open_id")
    @Schema(description = "用户在这个 OAuth2 客户端中的唯一标识")
    private String openId;
}
