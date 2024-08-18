package org.cainiao.authorizationcenter.entity.acl.organization;

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
 * 租户下有哪些用户<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tenant_user")
@Schema(name = "TenantUser", description = "租户用户")
public class TenantUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -8073685222904689538L;

    @TableField("tu_tenant_id")
    @Schema(description = "租户 ID")
    private long tenantId;

    @TableField("tu_user_id")
    @Schema(description = "平台用户 ID")
    private long userId;

    /**
     * 用户在这个租户中的唯一标识<br />
     * 租户范围的资源应该以这个 ID 来标识资源由哪个用户所有，而不是用平台用户ID<br />
     * 已达到用户ID在租户间隔离的目的，并且可用于将同一个用户在同一个租户内的多个【OAuth2 客户端的 openId】关联起来（类似飞书的union_id）
     */
    @TableField("tu_tenant_user_id")
    @Schema(description = "用户在租户中的唯一标识")
    private String tenantUserId;
}
