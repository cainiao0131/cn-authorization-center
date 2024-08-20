package org.cainiao.authorizationcenter.entity.acl.technology;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.dao.IdBaseEntity;

import java.io.Serial;

/**
 * TODO 考虑创建一个授权中心的库，将部分 OAuth2 Client 不用的类移过去<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_system")
@Schema(name = "System", description = "系统")
public class System extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4017908273219433506L;

    @TableField("sys_tenant_id")
    @Schema(description = "所属租户 ID")
    private long tenantId;

    @TableField("sys_access_scope")
    @Schema(description = "什么范围的用户可以访问这个系统，空表示所有用户都能访问")
    private AccessScopeEnum accessScope;

    @TableField("sys_client_id")
    @Schema(description = "系统绑定的 OAuth2 客户端 ID，允许暂不绑定，因此可以为空")
    private String clientId;

    @TableField("sys_name")
    @Schema(description = "系统名称")
    private String name;

    @Getter
    @AllArgsConstructor
    public enum AccessScopeEnum {
        SYSTEM_USER("systemUser", "系统内用户可访问"),
        TENANT_USER("tenantUser", "租户内用户可访问");

        final String code;
        final String description;
    }
}
