package org.cainiao.authorizationcenter.config;

import lombok.experimental.UtilityClass;
import org.springframework.core.Ordered;

/**
 * SecurityFilterChain 排序<br />
 * 分别是授权服务器、OAuth2 客户端登录、表单登录、资源服务器<br />
 * 排序的思路是，前边的 SecurityFilterChain 匹配不上自己的特定端点就漏给下一个<br />
 * 前边授权或登录的 SecurityFilterChain 都只匹配特定端点，如果是这些特定端点就进行授权或登录<br />
 * 授权中心的 OAuth2 客户端只是用来完成授权三方登录的，授权中心自己的前端不需要其拦截，因此配置为只匹配特定端点<br />
 * 表单登录要拦截授权中心自己的页面 AJAX 请求，将未鉴权用户重定向到登录页，在登录页可以选择三方登录<br />
 * <p>
 * 外部系统调用都是授权中心主机名加 /system/...，因此 OAuth2 客户端与表单登录的 FilterChain 避开这个前缀即可<br />
 * 如果不是授权或登录的特定端点，则都漏给资源服务器，资源服务器只认令牌或客户端凭据，令牌的情况还会根据令牌scope授权
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@UtilityClass
public class FilterChainOrder {

    public static final int AUTHORIZATION_SERVER_PRECEDENCE = Ordered.HIGHEST_PRECEDENCE;

    public static final int OAUTH2_CLIENT_LOGIN_PRECEDENCE = AUTHORIZATION_SERVER_PRECEDENCE + 1;

    public static final int FORM_LOGIN_PRECEDENCE = OAUTH2_CLIENT_LOGIN_PRECEDENCE + 1;

    public static final int RESOURCE_SERVER_PRECEDENCE = FORM_LOGIN_PRECEDENCE + 1;
}
