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

/**
 * Configuration class for Spring Security.
 * <p>
 * This class configures security settings for the application, including HTTP
 * security, authentication,
 * and password encoding. It sets up a filter chain to handle security aspects
 * such as authentication and
 * authorization, and provides the necessary beans for password encoding and
 * authentication management.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointImpl authEntryPoint;
    private JwtAuthFilter jwtAuthFilter;

    /**
     * Constructs a {@link SecurityConfig} with the specified
     * {@link UserDetailsServiceImpl},
     * {@link AuthEntryPointImpl}, and {@link JwtAuthFilter}.
     * 
     * @param userDetailsService the service used to load user-specific data
     * @param authEntryPoint     the entry point used to handle unauthorized access
     * @param jwtAuthFilter      the filter used to handle JWT authentication
     */
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointImpl authEntryPoint,
            JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * <p>
     * This method sets up security configurations, including disabling CSRF
     * protection,
     * permitting requests to certain endpoints, enforcing authentication for other
     * endpoints,
     * and adding the JWT authentication filter before the default
     * UsernamePasswordAuthenticationFilter.
     * </p>
     * 
     * @param http the {@link HttpSecurity} object used to configure security
     *             settings
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(permit -> permit.antMatchers("/api/auth/**").permitAll())
                .authorizeHttpRequests(auth -> auth.antMatchers(
                        "/api/auth/me",
                        "/api/**").authenticated())
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

    /**
     * Provides a {@link PasswordEncoder} bean for encoding passwords.
     * <p>
     * This bean uses {@link BCryptPasswordEncoder} for hashing passwords.
     * </p>
     * 
     * @return the {@link PasswordEncoder} bean
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an {@link AuthenticationManager} bean.
     * <p>
     * This bean is used to manage authentication processes.
     * </p>
     * 
     * @param authConfig the {@link AuthenticationConfiguration} object used to
     *                   obtain the {@link AuthenticationManager}
     * @return the {@link AuthenticationManager} bean
     * @throws Exception if an error occurs while creating the
     *                   {@link AuthenticationManager}
     */
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
