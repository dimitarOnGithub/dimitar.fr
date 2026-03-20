package fr.dimitar.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain postsChain(HttpSecurity httpSecurity) {
        httpSecurity
                .securityMatcher("/posts/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/posts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/posts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/posts/**").permitAll()
                );
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain authChain(HttpSecurity httpSecurity){
        httpSecurity
                .securityMatcher("/auth", "/myself")
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/myself").authenticated()
                )
                // Use session-based authentication
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()));

        return httpSecurity.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain draftsChain(HttpSecurity httpSecurity) {
        httpSecurity
                .securityMatcher("/drafts/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/drafts").authenticated()
                );
        return httpSecurity.build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain homeChain(HttpSecurity httpSecurity) {
        httpSecurity
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                );
        return httpSecurity.build();
    }
}
