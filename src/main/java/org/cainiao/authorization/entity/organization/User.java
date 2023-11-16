package org.cainiao.authorization.entity.organization;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.cainiao.common.utils.enums.Codebook;
import org.cainiao.common.utils.enums.ICodeBook;
import org.nutz.dao.entity.annotation.*;
import org.nutz.json.JsonField;

import java.io.Serial;

/**
 * 用户<br />
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
@Table("t_user")
@Comment("用户")
@Schema(name = "User", description = "用户")
public class User extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 661878493690894274L;

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    @Name
    @Column("u_name")
    @Comment("用户名")
    @ColDefine(notNull = true, width = 50, precision = 0)
    private String name;

    @Schema(description = "密码", requiredMode = RequiredMode.REQUIRED)
    @Column("u_password")
    @Comment("密码")
    @ColDefine(width = 128, precision = 0)
    private String password;

    @Schema(description = "手机号", requiredMode = RequiredMode.REQUIRED)
    @Column("u_mobile")
    @Comment("手机号")
    @ColDefine(width = 15, precision = 0)
    private String mobile;

    @Schema(description = "座机号码", requiredMode = RequiredMode.NOT_REQUIRED)
    @Column("u_phone")
    @Comment("座机号码")
    @ColDefine(width = 15, precision = 0)
    private String phone;

    @Schema(description = "邮箱", requiredMode = RequiredMode.REQUIRED)
    @Column("u_email")
    @Comment("邮箱")
    @ColDefine(width = 128, precision = 0)
    private String email;

    @Schema(description = "真实姓名", requiredMode = RequiredMode.REQUIRED)
    @Column("u_full_name")
    @Comment("真实姓名")
    @ColDefine(width = 50, precision = 0)
    private String fullName;

    @Schema(description = "用户头像", requiredMode = RequiredMode.AUTO)
    @Column("u_avatar")
    @Comment("用户头像")
    @ColDefine(width = 128, precision = 0)
    private String avatar;

    @Schema(description = "用户二维码", requiredMode = RequiredMode.AUTO)
    @Column("u_qr_code")
    @Comment("用户二维码")
    @ColDefine(width = 128, precision = 0)
    private String qrCode;

    @Schema(description = "性别", requiredMode = RequiredMode.REQUIRED)
    @Column("u_sex")
    @Comment("性别")
    @ColDefine(width = 20, precision = 0)
    @Default
    private Sex sex = Sex.FEMALE;

    @Schema(description = "是否禁用", requiredMode = RequiredMode.REQUIRED)
    @Column("u_disabled")
    @Comment("是否禁用")
    @ColDefine(width = 1, precision = 0)
    private boolean disabled;

    @Getter
    @AllArgsConstructor
    public enum Sex implements ICodeBook {
        MALE("MALE", "男"),
        FEMALE("FEMALE", "女");
        final String code;
        final String description;
    }

    @Getter
    @AllArgsConstructor
    public enum SearchField implements ICodeBook {
        FULL_NAME("u_full_name", "姓名"),
        DEPARTMENT("department", "部门"),
        MOBILE("u_mobile", "手机号码"),
        MAIL("u_email", "邮箱"),
        PHONE("u_phone", "座机号码");
        final String code;
        final String description;
    }

    @JsonGetter
    @JsonField
    public Codebook getSexInfo() {
        return getSex().build();
    }

    public void setSexInfo(Codebook sexInfo) {
        setSex(Sex.valueOf(sexInfo.getName()));
    }

}
