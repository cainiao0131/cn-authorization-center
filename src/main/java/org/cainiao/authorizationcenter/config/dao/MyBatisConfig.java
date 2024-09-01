package org.cainiao.authorizationcenter.config.dao;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.cainiao.oauth2.client.core.entity.typehandler.*;
import org.cainiao.oauth2.client.core.entity.typehandler.tool.ConfigurationMetadataMap;
import org.cainiao.oauth2.client.core.entity.typehandler.tool.ScopeSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            typeHandlerRegistry.register(AuthenticationMethod.class, AuthenticationMethodTypeHandler.class);
            typeHandlerRegistry.register(AuthorizationGrantType.class, AuthorizationGrantTypeTypeHandler.class);
            typeHandlerRegistry.register(ClientAuthenticationMethod.class, ClientAuthenticationMethodTypeHandler.class);
            typeHandlerRegistry.register(ConfigurationMetadataMap.class, ConfigurationMetadataMapTypeHandler.class);
            typeHandlerRegistry.register(ScopeSet.class, ScopeSetTypeHandler.class);
        };
    }
}
