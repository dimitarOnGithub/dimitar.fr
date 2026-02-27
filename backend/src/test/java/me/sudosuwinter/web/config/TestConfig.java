package me.sudosuwinter.web.config;

import me.sudosuwinter.web.auth.TestUserService;
import me.sudosuwinter.web.posts.PostService;
import me.sudosuwinter.web.posts.TestPostsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.jdbc.Sql;

@TestConfiguration
@Profile("test")
@Sql(scripts={"classpath:schemas/schema.sql", "classpath:schemas/test_posts.sql"})
public class TestConfig {

    @Bean
    public PostService testPostService(){
        return new TestPostsService();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new TestUserService();
    }

}
