package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * PostDto is a data transfer object representing a post in the system.
 * It is used for transferring data related to posts between different layers
 * of the application, such as between the client and the server or between
 * different components of the backend.
 * 
 * The class is annotated with {@link Data} from Lombok to automatically
 * generate
 * standard methods such as getters, setters, equals, hashCode, and toString.
 */
@Data
public class PostDto {

    /**
     * The unique identifier of the post.
     */
    private Long id;

    /**
     * The ID of the topic associated with the post.
     */
    @NotBlank
    private Long topicId;

    /**
     * The title of the post.
     */
    @NotBlank
    @Size(max = 100)
    private String title;

    /**
     * The ID of the author who created the post.
     */
    @NotBlank
    private Long authorId;

    /**
     * The main content of the post.
     */
    @NotBlank
    @Size(max = 10000)
    private String content;

    /**
     * The date and time when the post was created.
     */
    private LocalDateTime createdAt;
}
