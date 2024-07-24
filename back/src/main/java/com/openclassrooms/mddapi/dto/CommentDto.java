package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    private Long userId;

    private Long postId;

    private String message;

}
