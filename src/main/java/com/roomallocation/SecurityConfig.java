package com.roomallocation; // Change this to match your project package name

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // All pages require a login
            )
            .formLogin(form -> form
                .permitAll() // Allows access to the built-in login form
            )
            .logout(logout -> logout.permitAll()); // Enables logging out cleanly
        
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Create an Admin user with a hardcoded password for testing
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("password123")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }
}