package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * A request class for user login.
 * This class contains the necessary fields for authenticating a user,
 * including a username or email and a password.
 */
@Data
public class LoginRequest {

    /**
     * The username or email of the user attempting to log in.
     * This field must not be blank.
     */
    @NotBlank
    private String usernameOrEmail;

    /**
     * The password of the user attempting to log in.
     * This field must not be blank.
     */
    @NotBlank
    private String password;
}
