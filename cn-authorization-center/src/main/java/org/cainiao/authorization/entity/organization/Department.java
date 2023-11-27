package org.cainiao.authorization.entity.organization;

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
import org.nutz.dao.entity.annotation.*;
import org.nutz.json.JsonField;
import org.nutz.lang.random.R;

import java.io.Serial;
import java.util.Optional;

/**
 * 部门<br />
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
@Table("t_department")
@Comment("部门")
@Schema(name = "Department", description = "部门")
public class Department extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 539198033432189212L;

    @Schema(description = "部门编号", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("d_department_id")
    @Comment("部门编号")
    @ColDefine(notNull = true, width = 64, precision = 0)
    @Default
    private String departmentId = R.UU32();

    @Schema(description = "上级部门编号", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("d_parent_id")
    @Comment("上级部门编号")
    @ColDefine(notNull = false, width = 64, precision = 0)
    private String parentId;

    @Schema(description = "部门名称", requiredMode = RequiredMode.REQUIRED)
    @Column("d_name")
    @Comment("部门名称")
    @ColDefine(notNull = true, width = 128, precision = 0)
    private String name;

    @Schema(description = "部门类型", requiredMode = RequiredMode.REQUIRED)
    @Column("d_type")
    @Comment("部门类型")
    @ColDefine(notNull = true, width = 128, precision = 0)
    @Default
    private Type type = Type.DEPARTMENT;

    @Schema(description = "部门简称", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("d_simple_name")
    @Comment("部门简称")
    @ColDefine(notNull = false, width = 128, precision = 0)
    private String simpleName;

    @Schema(description = "部门全称", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("d_full_name")
    @Comment("部门全称")
    @ColDefine(notNull = false, width = 128, precision = 0)
    private String fullName;

    @Schema(description = "部门英文名称", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("d_english_name")
    @Comment("部门英文名称")
    @ColDefine(notNull = false, width = 500, precision = 0)
    private String englishName;

    @Schema(description = "序号", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("d_order")
    @Comment("序号")
    @ColDefine(notNull = false, width = 19, precision = 0)
    private Long order;

    @Getter
    @AllArgsConstructor
    public enum Type implements ICodeBook {
        /**
         * 
         */
        DEPARTMENT("DEPARTMENT", "部门"),
        /**
         * 
         */
        COMPANY("COMPANY", "公司"),
        /**
         * 
         */
        VIRTUAL("VIRTUAL", "虚拟机构");

        final String code;
        final String description;

    }

    @JsonField
    public Codebook getTypeInfo() {
        return Optional.of(getType()).orElse(Type.DEPARTMENT).build();
    }

    public void setTypeInfo(Codebook typeInfo) {
        setType(Type.valueOf(typeInfo.getName()));
    }

}
