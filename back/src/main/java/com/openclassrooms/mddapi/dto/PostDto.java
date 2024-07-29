package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Post data to transfer
 */
@Data
public class PostDto {

    private Long id;

    private Long topicId;

    private String title;

    private Long authorId;

    private String content;

    private LocalDateTime createdAt;
}
