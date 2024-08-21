package org.cainiao.authorizationcenter.util;

import lombok.experimental.UtilityClass;
import org.cainiao.authorizationcenter.entity.acl.User;
import org.cainiao.authorizationcenter.entity.authorizationserver.ClientUser;
import org.cainiao.common.util.DDLGenerator;
import org.cainiao.oauth2.client.core.entity.CnClientRegistration;

import java.io.IOException;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@UtilityClass
public class GenerateUtil {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DDLGenerator ddlGenerator = DDLGenerator
            .NEW("src/main/resources/sql/ddl.sql", User.class, ClientUser.class, CnClientRegistration.class);
        ddlGenerator.generateDDL();
    }
}
