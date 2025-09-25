package edu.cit.melocoton.jerickiel.campusequipmentloan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Cross-Site Request Forgery) for API calls
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        // Public Endpoints (Permit ALL)
                        // Registration and Login MUST be public so users can authenticate
                        .requestMatchers(HttpMethod.POST, "/api/students/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/students/login").permitAll()

                        // Secured Endpoints (Require Authentication)
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                );

        // Since we are using an API and not a browser, we won't use form-based login.
        // Any request to a secured endpoint without proper authentication will result in a 401 Unauthorized status.

        return http.build();
    }
}