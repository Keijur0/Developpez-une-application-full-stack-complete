package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.IPostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing posts.
 * This controller provides endpoints to handle requests related to {@link Post}
 * entities,
 * including retrieving individual posts, retrieving posts subscribed by a user,
 * and creating new posts.
 * 
 * <p>
 * All responses are wrapped in a {@link ResponseEntity} to provide appropriate
 * HTTP status codes and response bodies.
 * </p>
 * 
 * @see Post
 * @see PostDto
 * @see IPostService
 */
@RestController
@RequestMapping("/api/post")
@Tag(name = "Post endpoints")
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    /**
     * Retrieves a post by its unique identifier.
     * 
     * @param id the unique identifier of the post
     * @return a {@link ResponseEntity} containing the {@link PostDto} of the
     *         requested post
     */
    @Operation(description = "This endpoint is used to get a post with its id and returns the post if it exists.", summary = "Gets a post by id.", responses = {
            @ApiResponse(description = "Success: Returns the post.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The post does not exist in the database.", responseCode = "404")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    /**
     * Retrieves a list of posts that a user is subscribed to.
     * 
     * @param userId the unique identifier of the user
     * @return a {@link ResponseEntity} containing a list of {@link PostDto} objects
     */
    @Operation(description = "This endpoint is used to get all the posts having a topic a user has subscribed and returns the list of all posts.", summary = "Gets all posts a user has subscribed.", responses = {
            @ApiResponse(description = "Success: Returns the list of posts.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The user does not exist in the database.", responseCode = "404")
    })
    @GetMapping("/subscribed/{userId}")
    public ResponseEntity<?> getSubscribedPosts(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.getSubscribedPosts(userId));
    }

    /**
     * Creates a new post.
     * 
     * @param postDto the data transfer object containing the details of the post to
     *                be created
     * @return a {@link ResponseEntity} containing the created {@link PostDto}
     */
    @Operation(description = "This endpoint is used to create a post, with the information the user has provided in the creation form.", summary = "Creates a post.", responses = {
            @ApiResponse(description = "Success: Creates the post.", responseCode = "200"),
            @ApiResponse(description = "Bad request: The data entered is not valid.", responseCode = "400"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401")
    })
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }
}
