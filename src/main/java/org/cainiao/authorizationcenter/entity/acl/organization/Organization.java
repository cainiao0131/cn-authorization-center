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
 * 机构树<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_organization")
@Schema(name = "Organization", description = "机构树")
public class Organization extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -1893076163803657124L;

    @TableField("org_tenant_id")
    @Schema(description = "租户 ID")
    private long tenantId;

    @TableField("org_parent_id")
    @Schema(description = "父机构 ID")
    private Long parentId;

    @TableField("org_name")
    @Schema(description = "机构名称")
    private String name;
}
