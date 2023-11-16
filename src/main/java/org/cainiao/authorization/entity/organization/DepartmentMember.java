package org.cainiao.authorization.entity.organization;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serial;

/**
 * 部门成员<br />
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
@Table("t_department_member")
@Comment("部门成员")
@Schema(name = "DepartmentMember", description = "部门成员")
public class DepartmentMember extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4978545828925541967L;

    @Schema(description = "部门Id", requiredMode = RequiredMode.REQUIRED)
    @Column("dm_department_id")
    @Comment("部门Id")
    @ColDefine(width = 64, precision = 0)
    private String departmentId;

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @Column("dm_user_name")
    @Comment("用户名")
    @ColDefine(width = 50, precision = 0)
    private String userName;

    @Schema(description = "是否部门领导", requiredMode = RequiredMode.REQUIRED)
    @Column("dm_is_leader")
    @Comment("是否部门领导")
    @ColDefine(width = 1, precision = 0)
    private Boolean leader;

}
