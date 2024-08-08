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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for authentication-related endpoints.
 * Provides endpoints for user login and registration.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication endpoints")
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
    @Operation(description = "This endpoint is used to authenticate an existing user, using his credentials. It will return user information and a JWT token if it is successful.", summary = "Authenticates an existing user with his credentials.", responses = {
            @ApiResponse(description = "Success: User authenticated, user information and token sent.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: User not authenticated, wrong credentials or user does not exist", responseCode = "401")
    })
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
    @Operation(description = "This endpoint is used to save a new user in the database by filling a form, and returns a status code 200 if the registration is successful.", summary = "Saves a new user in the database by filling the register form.", responses = {
            @ApiResponse(description = "Success: New user saved.", responseCode = "200"),
            @ApiResponse(description = "Bad request: At least one field has not been filled, data entered is not valid, or username/email is already used", responseCode = "400")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    /**
     * Endpoint to get the authenticated user's details.
     *
     * @return a {@link ResponseEntity} containing the user's details
     */
    @Operation(description = "This endpoint is used to get the current authenticated user, using the security context and returns the user information.", summary = "Gets the current user from security context, returns user information.", responses = {
            @ApiResponse(description = "Success: Returns the user information.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: User is not authenticated, no information to return.", responseCode = "401")
    })
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return authService.me();
    }

}
