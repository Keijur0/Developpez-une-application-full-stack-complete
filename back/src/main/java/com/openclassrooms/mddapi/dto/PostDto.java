package com.openclassrooms.mddapi.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PostDto {

    private Long id;

    private Long topicId;

    private String title;

    private Long authorId;

    private String content;

    private Date createdAt;
}
