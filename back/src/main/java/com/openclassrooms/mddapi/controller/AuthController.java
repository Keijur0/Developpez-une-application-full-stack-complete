package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.service.IAuthService;

/**
 * Controller for authentication-related endpoints.
 * Provides endpoints for user login and registration.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    /**
     * Constructs an AuthController with the given authService.
     *
     * @param authService the authentication service
     */
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint for user login.
     * Validates the provided login request and returns an authentication response.
     *
     * @param loginRequest the login request containing username/email and password
     * @return a {@link ResponseEntity} containing the authentication response or an
     *         error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    /**
     * Endpoint for user registration.
     * Validates the provided registration request and returns a message indicating
     * the result.
     *
     * @param registerRequest the registration request containing user details
     * @return a {@link ResponseEntity} containing a success or error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    /**
     * Endpoint to get the authenticated user's details.
     *
     * @return a {@link ResponseEntity} containing the user's details
     */
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return authService.me();
    }

}
