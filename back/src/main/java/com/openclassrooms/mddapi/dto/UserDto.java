package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * User data to transfer
 */
@Data
public class UserDto {

    private Long id;

    private String email;
    
    private String username;
}
