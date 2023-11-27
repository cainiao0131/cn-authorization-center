package org.cainiao.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.util.constant.Codebook;
import org.cainiao.common.util.constant.ICodeBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.cainiao.auth.AuthUser.SexEnum.UNKNOWN;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED)
    String userName;

    @Schema(description = "姓名", requiredMode = RequiredMode.REQUIRED)
    String fullName;

    @Schema(description = "邮箱", requiredMode = RequiredMode.AUTO)
    String email;

    @Schema(description = "电话", requiredMode = RequiredMode.AUTO)
    String mobile;

    @Schema(description = "用户头像", requiredMode = RequiredMode.AUTO)
    private String avatar;

    @Schema(description = "性别", requiredMode = RequiredMode.AUTO)
    @Default
    private SexEnum sex = SexEnum.FEMALE;

    @Schema(description = "密码", requiredMode = RequiredMode.REQUIRED)
    @JsonIgnore
    String password;

    @Schema(description = "jwt Token", requiredMode = RequiredMode.REQUIRED)
    String token;

    @Schema(description = "jwt refreshToken", requiredMode = RequiredMode.REQUIRED)
    String refreshToken;

    @Schema(description = "角色列表", requiredMode = RequiredMode.REQUIRED)
    List<String> roles;

    @Schema(description = "权限列表", requiredMode = RequiredMode.REQUIRED)
    List<String> permissions;

    @Schema(description = "扩展信息", requiredMode = RequiredMode.NOT_REQUIRED)
    @Default
    Map<String, Object> extInfo = new HashMap<>();

    @Getter
    @AllArgsConstructor
    public enum SexEnum implements ICodeBook {
        MALE("MALE", "男"),
        FEMALE("FEMALE", "女"),
        UNKNOWN("UNKNOWN", "未知");

        final String code;
        final String description;
    }

    @JsonProperty
    public Codebook getSexInfo() {
        return getSex().build();
    }

    public static SexEnum getSex(String sexEnumName) {
        try {
            return SexEnum.valueOf(sexEnumName);
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 添加扩展信息
     *
     * @param key
     *            key
     * @param value
     *            value
     * @return 用户信息
     */
    public AuthUser addExt(String key, Object value) {
        getExtInfo().put(key, value);
        return this;
    }

}
