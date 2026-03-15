package fr.dimitar.web.config;

import fr.dimitar.web.auth.TestUserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;

@TestConfiguration
@Profile("test")
public class TestConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        return new TestUserService();
    }

}
