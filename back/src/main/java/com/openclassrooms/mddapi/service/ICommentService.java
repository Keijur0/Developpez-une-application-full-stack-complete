package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.CommentDto;

/**
 * Service interface for managing comments.
 * This interface defines the operations that can be performed on {@link CommentDto} objects,
 * including retrieving comments for a specific post and creating new comments.
 * 
 * <p>Implementations of this interface are expected to handle the business logic
 * and interaction with the data layer for comment-related operations.</p>
 * 
 * @see CommentDto
 */
public interface ICommentService {

    /**
     * Retrieves all comments from a post.
     * 
     * @param postId the id of the post
     * @return a list of {@link CommentDto}
     */
    List<CommentDto> getComments(Long postId);

    /**
     * Creates a new comment.
     * 
     * @param commentDto the data transfer object containing the details of the comment to be created
     * @return the created {@link CommentDto} with updated information, such as the generated id
     */
    CommentDto createComment(CommentDto commentDto);
}
