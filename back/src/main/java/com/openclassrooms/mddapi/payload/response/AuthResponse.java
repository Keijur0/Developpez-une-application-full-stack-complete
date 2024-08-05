package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A response class representing the authentication result.
 * This class typically contains a JWT token used for authenticating subsequent
 * requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    /**
     * The JWT token generated upon successful authentication.
     * This token is used to authenticate subsequent requests to the server.
     */
    private String token;

    /**
     * The unique identifier of the authenticated user.
     * This ID corresponds to the user's record in the database.
     */
    private Long id;

    /**
     * The username of the authenticated user.
     * This is the name used by the user to log in.
     */
    private String username;

    /**
     * The email address of the authenticated user.
     * This email may be used for contact and recovery purposes.
     */
    private String email;
}
