package org.cainiao.authorizationcenter.entity.acl;

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
 * TODO 考虑创建一个授权中心的库，将部分 OAuth2 Client 不用的类移过去<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_ui_module")
@Schema(name = "UIModule", description = "UI 模块")
public class UIModule extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -3395363283455945345L;

    @TableField("um_application_id")
    @Schema(description = "应用 ID")
    private long applicationId;

    @TableField("um_parent_id")
    @Schema(description = "父模块 ID")
    private Long parentId;

    @TableField("um_key")
    @Schema(description = "对应前端路由中的 name")
    private String key;

    @TableField("um_name")
    @Schema(description = "模块名称")
    private String name;

    @TableField("um_description")
    @Schema(description = "模块描述")
    private String description;

    @TableField("um_uri")
    @Schema(description = "UI 模块对应的前端 URI，用于登录后重放")
    private String uri;
}
