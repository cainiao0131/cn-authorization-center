package org.cainiao.authorizationcenter.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.Duration;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@UtilityClass
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Duration.class, new DurationSerializer());
        module.addDeserializer(Duration.class, new DurationDeserializer());
        OBJECT_MAPPER.registerModule(module);
    }

    public static String toJsonString(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T parseJson(String jsonString, TypeReference<T> typeReference) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonString, typeReference);
    }

    static class DurationSerializer extends JsonSerializer<Duration> {
        @Override
        public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

            jsonGenerator.writeString(duration.toString());
        }
    }

    static class DurationDeserializer extends JsonDeserializer<Duration> {
        @Override
        public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

            String durationString = jsonParser.getValueAsString();
            return Duration.parse(durationString);
        }
    }
}
