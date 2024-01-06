package org.cainiao.authorization.entity.acl;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.cainiao.common.util.constant.Codebook;
import org.cainiao.common.util.constant.ICodeBook;

import java.io.Serial;

/**
 * 权限<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "t_permission")
@Schema(name = "Permission", description = "权限")
public class Permission extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -3142769600946800480L;

    @Column(name = "p_key", length = 32)
    @Schema(description = "权限key,英文", requiredMode = RequiredMode.REQUIRED)
    private String key;

    @Column(name = "p_key_path", length = 200)
    @Schema(description = "权限keyPath,用来做业务,(父级keyPath.key)", requiredMode = RequiredMode.REQUIRED)
    private String keyPath;

    @Column(name = "p_name", length = 50)
    @Schema(description = "权限名称,中文用来做标识", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Column(name = "p_description", length = 128)
    @Schema(description = "权限描述", requiredMode = RequiredMode.AUTO)
    private String description;

    @Column(name = "p_client_id", length = 32)
    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    private String clientId;

    @Column(name = "p_parent_key", length = 32)
    @Schema(description = "父权限key", requiredMode = RequiredMode.AUTO)
    private String parentKey;

    @Column(name = "p_type", length = 20)
    @Schema(description = "权限类型", requiredMode = RequiredMode.REQUIRED)
    @Default
    private Type type = Type.MENU;

    @Getter
    @AllArgsConstructor
    public enum Type implements ICodeBook {
        /**
         * 菜单/页面
         */
        MENU("menu", "菜单"),
        /**
         * 按钮
         */
        BUTTON("button", "按钮"),
        /**
         * 其他页面元素
         */
        OTHER("other", "其他页面元素");

        @EnumValue
        final
        String code;
        final String description;
    }

    @JsonGetter
    public Codebook getTypeInfo() {
        return type == null ? null : type.build();
    }

    public void setTypeInfo(Codebook typeInfo) {
        setType(Type.valueOf(typeInfo.getName()));
    }

}
