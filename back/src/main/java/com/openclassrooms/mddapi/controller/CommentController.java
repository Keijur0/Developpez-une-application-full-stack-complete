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

/**
 * REST controller for managing comments.
 * This controller provides endpoints for retrieving and creating comments
 * related to specific posts. It uses {@link ICommentService} to interact with the business logic layer.
 * 
 * <p>All operations return a {@link ResponseEntity} to indicate the HTTP status and the response body.</p>
 * 
 * @see CommentDto
 * @see ICommentService
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves all comments for a specific post.
     * 
     * @param postId the unique identifier of the post
     * @return a {@link ResponseEntity} containing a list of {@link CommentDto} objects
     */
    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    /**
     * Creates a new comment for a specific post.
     * 
     * @param commentDto the data transfer object containing the details of the comment to be created
     * @return a {@link ResponseEntity} containing the created {@link CommentDto}
     */
    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(commentDto));
    }
}
