package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.PostDto;

/**
 * Service interface for managing posts.
 * This interface defines the operations that can be performed on {@link PostDto} objects.
 * It includes methods for retrieving, creating, and fetching posts based on user subscriptions.
 * 
 * <p>Implementations of this interface are expected to provide the actual logic for interacting with the data layer or other services.</p>
 * 
 * @see PostDto
 */
public interface IPostService {

    /**
     * Retrieves a post by id.
     * 
     * @param id the unique identifier of the post to retrieve
     * @return the {@link PostDto} corresponding to the specified id
     */
    PostDto getPost(Long id);

    /**
     * Creates a new post.
     * 
     * @param postDto the data transfer object containing the details of the post to be created
     * @return the created {@link PostDto} with updated information, such as the generated id
     */
    PostDto createPost(PostDto postDto);

    /**
     * Retrieves a list of posts that the user is subscribed to.
     * 
     * @param userId the id of the user
     * @return a list of {@link PostDto} objects the user is subscribed to
     */
    List<PostDto> getSubscribedPosts(Long userId);
}
