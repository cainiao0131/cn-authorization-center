package org.cainiao.authorization.entity.organization;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.IdBaseEntity;
import org.cainiao.common.util.constant.Codebook;
import org.cainiao.common.util.constant.ICodeBook;
import org.hibernate.annotations.Comment;
import org.nutz.json.JsonField;

import java.io.Serial;

/**
 * 用户<br />
 *
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Entity
@Table(name = "t_user")
@Schema(name = "User", description = "用户")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = 661878493690894274L;

    private static final String NAME_DESCRIPTION = "用户名";
    private static final String PASSWORD_DESCRIPTION = "密码";
    private static final String MOBILE_DESCRIPTION = "手机号";
    private static final String SEX_DESCRIPTION = "性别";
    private static final String DISABLED_DESCRIPTION = "是否禁用";

    @Column(name = "u_username", nullable = false, length = 50, unique = true)
    @Comment(NAME_DESCRIPTION)
    @Schema(description = NAME_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    private String username;

    @Column(name = "u_password", length = 128)
    @Comment(PASSWORD_DESCRIPTION)
    @Schema(description = PASSWORD_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    private String password;

    @Column(name = "u_mobile", length = 15)
    @Comment(MOBILE_DESCRIPTION)
    @Schema(description = MOBILE_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    private String mobile;

    @Column(name = "u_sex", length = 20)
    @Comment(SEX_DESCRIPTION)
    @Schema(description = SEX_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
    @Default
    private Sex sex = Sex.FEMALE;

    @Column(name = "u_disabled", length = 1)
    @Comment(DISABLED_DESCRIPTION)
    @Schema(description = DISABLED_DESCRIPTION, requiredMode = RequiredMode.REQUIRED)
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
