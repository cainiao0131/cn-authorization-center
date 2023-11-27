package org.cainiao.authorization.config.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import org.cainiao.authorization.service.organization.UserService;
import org.nutz.lang.Lang;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RequiredArgsConstructor
public class UserInfoDetailsService implements UserDetailsService {

    private final UserService userService;

    LoadingCache<String, UserInfo> cache = CacheBuilder.newBuilder()
        .concurrencyLevel(8)
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .initialCapacity(10)
        .maximumSize(300)
        .recordStats()
        .build(new CacheLoader<>() {
            @Override
            public UserInfo load(String username) throws Exception {
                return userService.loadUserByUsername(username);
            }
        });

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return cache.get(username);
        } catch (ExecutionException e) {
            throw Lang.wrapThrow(e);
        }
    }

}
