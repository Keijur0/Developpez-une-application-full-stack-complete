package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.service.ICommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing comments.
 * This controller provides endpoints for retrieving and creating comments
 * related to specific posts. It uses {@link ICommentService} to interact with
 * the business logic layer.
 * 
 * <p>
 * All operations return a {@link ResponseEntity} to indicate the HTTP status
 * and the response body.
 * </p>
 * 
 * @see CommentDto
 * @see ICommentService
 */
@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment endpoints")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves all comments for a specific post.
     * 
     * @param postId the unique identifier of the post
     * @return a {@link ResponseEntity} containing a list of {@link CommentDto}
     *         objects
     */
    @Operation(description = "This endpoint is used to get all the comments from a post, and returns the list of comments.", summary = "Gets all comments from a post.", responses = {
            @ApiResponse(description = "Success: Returns the comments.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The post does not exist in the database.", responseCode = "404")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    /**
     * Creates a new comment for a specific post.
     * 
     * @param commentDto the data transfer object containing the details of the
     *                   comment to be created
     * @return a {@link ResponseEntity} containing the created {@link CommentDto}
     */
    @Operation(description = "This endpoint is used to create a new comment under a post. The content of the message is provided by the user.", summary = "Creates a comment to a post.", responses = {
            @ApiResponse(description = "Success: Creates the comment.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
    })
    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(commentDto));
    }
}
