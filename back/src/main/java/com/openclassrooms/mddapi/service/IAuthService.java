package com.openclassrooms.mddapi.service;

import org.springframework.http.ResponseEntity;

import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;

/**
 * Interface for authentication services.
 * Provides methods for logging in and registering users.
 */
public interface IAuthService {

    /**
     * Authenticates a user with the provided login request.
     *
     * @param loginRequest the login request containing the user's credentials
     * @return a {@link ResponseEntity} containing a success or error message
     */
    ResponseEntity<?> login(LoginRequest loginRequest);

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest the registration request containing the user's registration details
     * @return a {@link ResponseEntity} containing a success or error message
     */
    ResponseEntity<?> register(RegisterRequest registerRequest);
}
