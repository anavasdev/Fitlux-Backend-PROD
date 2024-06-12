package adnavas.fitlux_backend;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;


@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new ZonedDateTimeToStringConverter(),
                new StringToZonedDateTimeConverter()
        ));
    }

    static class ZonedDateTimeToStringConverter implements Converter<LocalDateTime, String> {
        @Override
        public String convert(LocalDateTime source) {
            return source.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    static class StringToZonedDateTimeConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}

