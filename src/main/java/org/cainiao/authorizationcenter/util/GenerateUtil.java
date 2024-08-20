package org.cainiao.authorizationcenter.util;

import lombok.experimental.UtilityClass;
import org.cainiao.common.dao.generator.DDLGenerator;

import java.io.IOException;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@UtilityClass
public class GenerateUtil {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DDLGenerator ddlGenerator = new DDLGenerator("org.cainiao.authorizationcenter.entity", "src/main/resources/sql/ddl.sql");
        ddlGenerator.generateDDL();
    }
}
