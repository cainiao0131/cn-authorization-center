package org.cainiao.authorizationcenter.entity.acl.technology;

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
 * TODO 考虑创建一个授权中心的库，将部分 OAuth2 Client 不用的类移过去<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_application")
@Schema(name = "Application", description = "应用")
public class Application extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4505057660743337320L;

    @TableField("app_system_id")
    @Schema(description = "所属系统 ID")
    private long systemId;

    @TableField("app_name")
    @Schema(description = "应用名称")
    private String name;
}
