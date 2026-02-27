package me.sudosuwinter.web.config;

import me.sudosuwinter.web.posts.Post;
import me.sudosuwinter.web.posts.PostsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class LocalConfig {

    // Ensure the usage of UTC timestamps
    // https://docs.spring.io/spring-data/jpa/reference/auditing.html
    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(Instant.now());
    }

    @Bean
    public CommandLineRunner dataLoader(PostsRepository postsRepository){
        postsRepository.saveAll(
                List.of(
                    new Post("Test Post 1", "Test Content", false),
                    new Post("Test Post 2", "Test Content", false),
                    new Post("Draft Test Post 1", "Test Draft Content", true)
                )
        );

        return args -> System.out.println("Created 3 posts");
    }

}
