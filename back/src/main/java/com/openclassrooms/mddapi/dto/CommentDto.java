package com.openclassrooms.mddapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CommentDto is a data transfer object representing a comment in the system.
 * It is used to transfer data between different layers of the application,
 * specifically between the client and server or between different components of
 * the backend.
 * 
 * The class is annotated with {@link Data} from Lombok to automatically
 * generate
 * boilerplate code like getters, setters, equals, hashCode, and toString
 * methods.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * The unique identifier of the comment.
     */
    private Long id;

    /**
     * The ID of the user who made the comment.
     */
    @NotNull
    private Long userId;

    /**
     * The ID of the post associated with the comment.
     */
    @NotNull
    private Long postId;

    /**
     * The message content of the comment.
     */
    @Size(max = 2000)
    @NotBlank
    private String message;

}
