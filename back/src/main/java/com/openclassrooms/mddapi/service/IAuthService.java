package com.openclassrooms.mddapi.service;

import org.springframework.http.ResponseEntity;

import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;

/**
 * Interface for authentication services.
 * <p>
 * This interface provides methods for user authentication and registration.
 * Implementations of this interface handle user login, registration, and
 * retrieval
 * of the current user's details.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
public interface IAuthService {

    /**
     * Authenticates a user based on the provided login request.
     * <p>
     * This method processes the user's credentials and returns a response entity
     * containing
     * a success or error message based on the authentication result.
     * </p>
     * 
     * @param loginRequest the login request containing the user's credentials
     * @return a {@link ResponseEntity} containing an authentication result, which
     *         may
     *         include a success or error message
     */
    ResponseEntity<?> login(LoginRequest loginRequest);

    /**
     * Registers a new user based on the provided registration request.
     * <p>
     * This method processes the user's registration details and returns a response
     * entity
     * containing a success or error message based on the registration result.
     * </p>
     * 
     * @param registerRequest the registration request containing the user's
     *                        registration
     *                        details
     * @return a {@link ResponseEntity} containing a success or error message
     */
    ResponseEntity<?> register(RegisterRequest registerRequest);

    /**
     * Retrieves the details of the currently authenticated user.
     * <p>
     * This method returns a response entity containing the current user's details,
     * such as
     * username and email.
     * </p>
     * 
     * @return a {@link ResponseEntity} containing the details of the currently
     *         authenticated user
     */
    ResponseEntity<?> me();
}
