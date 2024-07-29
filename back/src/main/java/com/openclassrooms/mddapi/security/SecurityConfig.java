package com.openclassrooms.mddapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.openclassrooms.mddapi.security.exception.AuthEntryPointImpl;
import com.openclassrooms.mddapi.security.filter.JwtAuthFilter;
import com.openclassrooms.mddapi.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointImpl authEntryPoint;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointImpl authEntryPoint,
            JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(permit ->
                permit.antMatchers("/api/auth/**").permitAll())
            .authorizeHttpRequests(auth ->
                auth.antMatchers("/api/**").authenticated())
            .userDetailsService(userDetailsService)
            .sessionManagement(session -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(authEntryPoint);
            })
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
