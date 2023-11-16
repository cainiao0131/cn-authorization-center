package org.cainiao.authorization.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import java.io.Serial;
import java.io.Serializable;

/**
 * Author: Cai Niao(wdhlzd@163.com)
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3559689069530048334L;

    public <T extends Entity> T exchange(Class<T> clazz) {
        return Json.fromJson(clazz, Json.toJson(this, JsonFormat.compact().ignoreJsonShape()));
    }

    @Override
    public String toString() {
        return Json.toJson(this, JsonFormat.nice());
    }

}
