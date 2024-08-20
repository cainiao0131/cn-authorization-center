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
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tenant")
@Schema(name = "Tenant", description = "租户")
public class Tenant extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -8073685222904689538L;

    @TableField("ten_name")
    @Schema(description = "租户名称")
    private String name;
}
