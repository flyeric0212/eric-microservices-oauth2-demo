package top.flyeric.auth.infrastructure.config.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.jackson2.CoreJackson2Module;
import top.flyeric.auth.infrastructure.config.security.userdetails.CustomUserDetails;
import top.flyeric.auth.infrastructure.config.security.userdetails.CustomUserDetailsMixin;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

@Configuration
public class JacksonConfiguration {

    public static final DateTimeFormatter CUSTOM_ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter CUSTOM_ISO_LOCAL_TIME;

    static {
        CUSTOM_ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(SECOND_OF_MINUTE, 2)
                .toFormatter();

        CUSTOM_ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(":")
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(":")
                .appendValue(SECOND_OF_MINUTE, 2)
                .toFormatter();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(CUSTOM_ISO_LOCAL_DATE_TIME))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(CUSTOM_ISO_LOCAL_DATE_TIME))
                .serializerByType(LocalDate.class, new LocalDateSerializer(ISO_LOCAL_DATE))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(ISO_LOCAL_DATE))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(CUSTOM_ISO_LOCAL_TIME))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(CUSTOM_ISO_LOCAL_TIME))
                .serializerByType(Instant.class, instantSerializer())
                .deserializerByType(Instant.class, instantDeserializer())
                .build();
    }

    private JsonDeserializer<Instant> instantDeserializer() {
        return new JsonDeserializer<Instant>() {
            @Override
            public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return Instant.ofEpochMilli(p.getValueAsLong());
            }
        };
    }

    private JsonSerializer<Instant> instantSerializer() {
        return new JsonSerializer<Instant>() {
            @Override
            public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value.toEpochMilli());
            }
        };
    }
}
