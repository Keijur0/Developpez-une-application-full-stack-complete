package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A response class representing the authentication result.
 * This class typically contains a JWT token used for authenticating subsequent requests.
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    /**
     * The JWT token generated upon successful authentication.
     */
    private String token;
}
