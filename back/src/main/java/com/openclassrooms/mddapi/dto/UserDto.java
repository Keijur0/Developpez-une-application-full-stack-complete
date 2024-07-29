package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.Data;

/**
 * User data to transfer
 */
@Data
public class UserDto {

    private Long id;

    private String email;
    
    private String username;

    private List<Long> subscriptionsId;
}
