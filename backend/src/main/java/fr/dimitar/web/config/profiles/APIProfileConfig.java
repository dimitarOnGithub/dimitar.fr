package fr.dimitar.web.config.profiles;

import fr.dimitar.web.config.BaseConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(BaseConfig.class)
@Profile("api")
public class APIProfileConfig {
}
