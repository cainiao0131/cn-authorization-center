package org.cainiao.authorization.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 实体基础类<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false, of = "id")
public class IdBaseEntity extends Entity {

    @Serial
    private static final long serialVersionUID = -1574346548208149719L;

    @Id
    private long id;

    @Schema(description = "创建时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("created_time")
    @Comment("创建时间")
    @Default
    protected LocalDateTime createdTime = LocalDateTime.now();

    @Schema(description = "最后更新时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("updated_time")
    @Comment("最后更新时间")
    @Default
    protected LocalDateTime updatedTime = LocalDateTime.now();

    @Schema(description = "创建人", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("created_by")
    @Comment("创建人")
    protected String createdBy;

    @Schema(description = "更新人", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("updated_by")
    @Comment("更新人")
    protected String updatedBy;

}
