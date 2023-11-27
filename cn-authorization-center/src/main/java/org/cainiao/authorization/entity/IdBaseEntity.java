package org.cainiao.authorization.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 实体基础类<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false, of = "id")
public class IdBaseEntity extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1574346548208149719L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Schema(description = "创建时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Default
    protected LocalDateTime createdTime = LocalDateTime.now();

    @Column(nullable = false)
    @Schema(description = "最后更新时间", requiredMode = RequiredMode.NOT_REQUIRED)
    @Default
    protected LocalDateTime updatedTime = LocalDateTime.now();

    @Column
    @Schema(description = "创建人", requiredMode = RequiredMode.NOT_REQUIRED)
    protected String createdBy;

    @Column
    @Schema(description = "更新人", requiredMode = RequiredMode.NOT_REQUIRED)
    protected String updatedBy;

}
