package org.cainiao.authorization.service.organization;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cainiao.authorization.config.init.AdministratorInitializationConfigurationProperties;
import org.cainiao.authorization.config.security.UserInfo;
import org.cainiao.authorization.entity.organization.User;
import org.cainiao.authorization.repository.UserRepository;
import org.cainiao.authorization.service.acl.ClientUserService;
import org.cainiao.authorization.service.acl.PermissionService;
import org.cainiao.authorization.service.acl.RoleService;
import org.cainiao.common.util.exception.BusinessException;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(AdministratorInitializationConfigurationProperties.class)
public class UserService {

    private static final String HAS_KEY = "hasKey";

    private final UserRepository userRepository;

    private final ClientUserService clientUserService;
    private final PermissionService permissionService;
    private final AdministratorInitializationConfigurationProperties configurationProperties;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentMemberService departmentMemberService;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    /**
     * 本地缓存时间要短于 token 过期时间，避免缓存到过期的 token
     */
    LoadingCache<String, UserInfo> cache = CacheBuilder.newBuilder()
        .concurrencyLevel(8)
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .initialCapacity(10)
        .maximumSize(300)
        .recordStats()
        .build(new CacheLoader<>() {
            @Override
            public UserInfo load(String username) throws Exception {
                User user = userRepository.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException(String.format("'%s' 用户不存在", username));
                }
                String clientId = configurationProperties.getClient().getClientId();
                return UserInfo.builder()
                    .username(user.getUsername())
                    .sex(user.getSex())
                    .disabled(user.isDisabled())
                    .mobile(user.getMobile())
                    .password(user.getPassword())
                    .permissions(permissions(username, clientId))
                    .roles(roles(username, clientId))
                    .token(jwtEncoder.encode(JwtEncoderParameters.from(JwtClaimsSet.builder()
                            .issuer("http://localhost:5173/api")
                            .subject(username)
                            .issuedAt(Instant.now())
                            .expiresAt(Instant.now()
                                .plusSeconds(60
                                    * 60
                                    * 24
                                    * 7L)) // 7天
                            .build()))
                        .getTokenValue())
                    .refreshToken(jwtEncoder.encode(JwtEncoderParameters
                            .from(JwtClaimsSet.builder()
                                .subject(username)
                                .issuedAt(Instant.now())
                                .expiresAt(Instant.now()
                                    .plusSeconds(60
                                        * 60
                                        * 24
                                        * 30L)) // 30天
                                .build()))
                        .getTokenValue())
                    .build();
            }
        });

    public UserInfo loadUserByUsername(String username) {
        if (Strings.isBlank(username)) {
            log.error("username is blank");
            return null;
        }
        try {
            return cache.get(username);
        } catch (ExecutionException e) {
            throw Lang.wrapThrow(e);
        }
    }

    public UserInfo loadUserByToken(String token) {
        if (Strings.isBlank(token)) {
            return null;
        }
        try {
            return loadUserByUsername(jwtDecoder.decode(token).getSubject());
        } catch (Exception e) {
            log.error("jwtDecoder.decode fail", e);
            return null;
        }
    }

    // TODO
    private List<String> roles(String name, String clientId) {
        return Collections.emptyList();
    }

    // TODO
    private List<String> permissions(String name, String clientId) {
        return Collections.emptyList();
    }

    public String resetPassword(long userId) {
        String password = R.sg(8).next();
        // 密码加密
        if (userRepository.setPasswordFor(passwordEncoder.encode(password), userId) > 0) {
            return password;
        }
        throw BusinessException.create("重置用户密码失败!");
    }

}
