package org.cainiao.authorization.config.init;

import lombok.Data;
import org.cainiao.authorization.dto.request.RegisteredClientInfo;
import org.cainiao.authorization.entity.acl.Role;
import org.cainiao.authorization.entity.oauth2.RegisteredClient;
import org.cainiao.authorization.entity.organization.User;
import org.nutz.lang.Lang;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@ConfigurationProperties("cn.init")
public class AdministratorInitializationConfigurationProperties {

    boolean enabled = true;

    User administer = User.builder()
        .username("cainiao")
        .mobile("17787747484")
        .password("G00dl^ck")
        .sex(User.Sex.MALE)
        .build();

    /**
     * oauth2客户端
     */
    RegisteredClientInfo client = RegisteredClientInfo.builder()
        .projectKey("cn-authorization-center")
        .serviceId("cn-authorization-center")
        .clientId("test")
        .clientSecret("test")
        .clientName("授权中心")
        .scopes(Lang.list(RegisteredClient.Scope.OPENID, RegisteredClient.Scope.PROFILE))
        .redirectUris(Lang.list("http://127.0.0.1:8888/login/oauth2/code/oauth2-client-oidc"))
        .clientAuthenticationMethods(Lang.list(RegisteredClient.AuthenticationMethod.values()))
        .authorizationGrantTypes(Lang.list(RegisteredClient.GrantType.values()))
        .build();

    Role admin = Role.builder()
        .key("admin")
        .name("超级管理员")
        .description("初始化角色")
        .build();

}
