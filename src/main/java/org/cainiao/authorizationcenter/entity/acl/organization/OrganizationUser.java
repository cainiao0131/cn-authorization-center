package org.cainiao.authorizationcenter.entity.acl.organization;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.dao.IdBaseEntity;

import java.io.Serial;

/**
 * 机构下有哪些用户<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_organization_user")
@Schema(name = "OrganizationUser", description = "机构用户")
public class OrganizationUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 3379428676742293336L;

    @TableField("ou_organization_id")
    @Schema(description = "机构 ID")
    private long organizationId;

    @TableField("ou_user_id")
    @Schema(description = "用户 ID")
    private long userId;
}
