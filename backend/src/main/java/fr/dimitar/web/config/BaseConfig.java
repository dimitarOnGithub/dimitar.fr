package fr.dimitar.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class BaseConfig {
    /*
    This config should only hold configuration stuff
    that applies to all environments and profiles
    */

    // Ensure the usage of UTC timestamps
    // https://docs.spring.io/spring-data/jpa/reference/auditing.html
    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(Instant.now());
    }

}
