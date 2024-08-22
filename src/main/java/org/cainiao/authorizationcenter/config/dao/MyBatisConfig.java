package org.cainiao.authorizationcenter.config.dao;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.AuthorizationGrantTypeSetTypeHandler;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.ClientAuthenticationMethodSetTypeHandler;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.ClientSettingsTypeHandler;
import org.cainiao.authorizationcenter.entity.authorizationserver.typehandler.TokenSettingsTypeHandler;
import org.cainiao.oauth2.client.core.entity.typehandler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.util.Map;
import java.util.Set;

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
            typeHandlerRegistry.register(Set.class, AuthorizationGrantTypeSetTypeHandler.class);
            typeHandlerRegistry.register(Set.class, ClientAuthenticationMethodSetTypeHandler.class);
            typeHandlerRegistry.register(ClientSettings.class, ClientSettingsTypeHandler.class);
            typeHandlerRegistry.register(TokenSettings.class, TokenSettingsTypeHandler.class);

            typeHandlerRegistry.register(AuthenticationMethod.class, AuthenticationMethodTypeHandler.class);
            typeHandlerRegistry.register(AuthorizationGrantType.class, AuthorizationGrantTypeTypeHandler.class);
            typeHandlerRegistry.register(ClientAuthenticationMethod.class, ClientAuthenticationMethodTypeHandler.class);
            typeHandlerRegistry.register(Map.class, MapTypeHandler.class);
            typeHandlerRegistry.register(Set.class, SetTypeHandler.class);
        };
    }
}
