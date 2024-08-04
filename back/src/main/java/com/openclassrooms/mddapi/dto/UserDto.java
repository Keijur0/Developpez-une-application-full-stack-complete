package com.openclassrooms.mddapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * UserDto is a data transfer object representing a user in the system.
 * It is used for transferring user-related data between different layers
 * of the application, such as between the client and the server or between
 * different components of the backend.
 * 
 * The class is annotated with {@link Data} from Lombok to automatically
 * generate
 * standard methods such as getters, setters, equals, hashCode, and toString.
 */
@Data
public class UserDto {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The email address of the user.
     */
    @NotBlank
    @Email
    private String email;

    /**
     * The username of the user.
     */
    @NotBlank
    private String username;
}
