package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageReponse;

/**
 * Interface for authentication services.
 * Provides methods for logging in and registering users.
 */
public interface IAuthService {

    /**
     * Authenticates a user with the provided login request.
     *
     * @param loginRequest the login request containing the user's credentials
     * @return an {@link AuthResponse} containing authentication details such as JWT token and user information
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest the registration request containing the user's registration details
     * @return a {@link MessageReponse} indicating the success or failure of the registration process
     */
    MessageReponse register(RegisterRequest registerRequest);
}
