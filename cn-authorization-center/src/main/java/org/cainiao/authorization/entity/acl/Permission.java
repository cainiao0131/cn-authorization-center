package org.cainiao.authorization.entity.acl;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.cainiao.common.util.constant.Codebook;
import org.cainiao.common.util.constant.ICodeBook;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;

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
@Table("t_permission")
@Comment("权限")
@Schema(name = "Permission", description = "权限")
public class Permission extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -3142769600946800480L;

    @Schema(description = "权限key,英文", requiredMode = RequiredMode.REQUIRED)
    @Column("p_key")
    @Comment("权限key,英文")
    @ColDefine(notNull = false, width = 32, precision = 0)
    private String key;

    @Schema(description = "权限keyPath,用来做业务,(父级keyPath.key)", requiredMode = RequiredMode.REQUIRED)
    @Column("p_key_path")
    @Comment("权限keyPath,用来做业务,(父级keyPath.key)")
    @ColDefine(width = 200, precision = 0)
    private String keyPath;

    @Schema(description = "权限名称,中文用来做标识", requiredMode = RequiredMode.REQUIRED)
    @Column("p_name")
    @Comment("权限名称,中文用来做标识")
    @ColDefine(width = 50, precision = 0)
    private String name;

    @Schema(description = "权限描述", requiredMode = RequiredMode.AUTO)
    @Column("p_description")
    @Comment("权限描述")
    @ColDefine(width = 128, precision = 0)
    private String description;

    @Schema(description = "客户端(应用)id", requiredMode = RequiredMode.REQUIRED)
    @Column("p_client_id")
    @Comment("客户端(应用)id")
    @ColDefine(width = 32, precision = 0)
    private String clientId;

    @Schema(description = "父权限key", requiredMode = RequiredMode.AUTO)
    @Column("p_parent_key")
    @Comment("父权限key")
    @ColDefine(width = 32, precision = 0)
    private String parentKey;

    @Schema(description = "权限类型", requiredMode = RequiredMode.REQUIRED)
    @Column("p_type")
    @Comment("权限类型")
    @ColDefine(width = 20, precision = 0)
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
    @JsonField
    public Codebook getTypeInfo() {
        return type == null ? null : type.build();
    }

    public void setTypeInfo(Codebook typeInfo) {
        setType(Type.valueOf(typeInfo.getName()));
    }

}
