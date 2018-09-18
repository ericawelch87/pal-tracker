package io.pivotal.pal.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PalTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }

    @Bean
    public TimeEntryRepository timeEntryRepository(){
        return new InMemoryTimeEntryRepository();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper result = new ObjectMapper();
            result.registerModule(new JavaTimeModule());
            result.disable(SerializationFeature.CLOSE_CLOSEABLE.WRITE_DATES_AS_TIMESTAMPS);
        return result;

    }
}
