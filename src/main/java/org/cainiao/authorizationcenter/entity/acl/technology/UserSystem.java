package org.cainiao.authorizationcenter.entity.acl.technology;

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
 * 用户可以访问哪些系统<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_system")
@Schema(name = "UserSystem", description = "用户系统")
public class UserSystem extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 678543534836422010L;

    @TableField("us_user_id")
    @Schema(description = "用户 ID")
    private long userId;

    @TableField("us_system_id")
    @Schema(description = "系统 ID")
    private long systemId;
}
