package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * A request class for user registration.
 * This class contains the necessary fields for registering a new user,
 * including username, email, and password.
 */
@Data
public class RegisterRequest {

    /**
     * The username for the new user.
     * This field must not be blank.
     */
    @NotBlank
    private String username;

    /**
     * The email address for the new user.
     * This field must not be blank and must be a valid email format.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The password for the new user.
     * This field must not be blank.
     */
    @NotBlank
    private String password;
}
