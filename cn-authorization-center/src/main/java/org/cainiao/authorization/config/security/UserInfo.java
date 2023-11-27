package org.cainiao.authorization.config.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.authorization.entity.organization.User;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserInfo<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 739935589691571511L;

    private List<String> roles;

    private List<String> permissions;

    @Schema(description = "token", requiredMode = Schema.RequiredMode.REQUIRED)
    String token;

    @Schema(description = "refreshToken", requiredMode = Schema.RequiredMode.REQUIRED)
    String refreshToken;

    public static UserInfo from(User user) {
        return Json.fromJson(UserInfo.class, Json.toJson(user));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lang.isEmpty(getRoles())
            ? Lang.list()
            : getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
