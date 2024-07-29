package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * Comment Data to transfer
 */
@Data
public class CommentDto {

    private Long id;

    private Long userId;

    private Long postId;

    private String message;

}
