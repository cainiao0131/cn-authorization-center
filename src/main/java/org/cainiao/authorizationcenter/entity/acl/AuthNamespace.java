package org.cainiao.authorizationcenter.entity.acl;

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
 * 用于三方服务扩展【角色】【权限】的设计和授权<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_auth_namespace")
@Schema(name = "AuthNamespace", description = "权限命名空间")
public class AuthNamespace extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 6706486012192579383L;

    @TableField("an_name")
    @Schema(description = "权限命名空间名称")
    private String name;

    @TableField("an_description")
    @Schema(description = "权限命名空间描述")
    private String description;
}
