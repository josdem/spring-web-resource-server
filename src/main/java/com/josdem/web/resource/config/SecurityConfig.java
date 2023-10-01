package com.josdem.web.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.mvcMatcher("/categories/**")
                .authorizeRequests()
                .mvcMatchers("/categories/**")
                .access("hasAuthority('SCOPE_categories.read')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
