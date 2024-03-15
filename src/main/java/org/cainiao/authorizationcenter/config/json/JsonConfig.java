package org.cainiao.authorizationcenter.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Configuration
public class JsonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Duration.class, new DurationSerializer());
        module.addDeserializer(Duration.class, new DurationDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
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
