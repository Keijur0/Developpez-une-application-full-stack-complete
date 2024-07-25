package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * REST controller for managing posts.
 * This controller provides endpoints to handle requests related to {@link Post} entities,
 * including retrieving individual posts, retrieving posts subscribed by a user, and creating new posts.
 * 
 * <p>All responses are wrapped in a {@link ResponseEntity} to provide appropriate HTTP status codes and response bodies.</p>
 * 
 * @see Post
 * @see PostDto
 * @see IPostService
 */
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private IPostService postService;

    /**
     * Retrieves a post by its unique identifier.
     * 
     * @param id the unique identifier of the post
     * @return a {@link ResponseEntity} containing the {@link PostDto} of the requested post
     */
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
    @GetMapping("/subscribed/{userId}")
    public ResponseEntity<?> getSubscribedPosts(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.getSubscribedPosts(userId));
    }

    /**
     * Creates a new post.
     * 
     * @param postDto the data transfer object containing the details of the post to be created
     * @return a {@link ResponseEntity} containing the created {@link PostDto}
     */
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }
}
