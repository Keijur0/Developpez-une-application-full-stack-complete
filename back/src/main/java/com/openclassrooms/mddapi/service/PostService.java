package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service for managing posts in the application.
 * <p>
 * Provides methods to retrieve and create posts.
 * Interacts with {@link PostRepository} and {@link UserRepository}.
 * </p>
 */
@Service
public class PostService implements IPostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final PostMapper postMapper;

	/**
	 * Constructs a new {@link PostService} with the specified repositories and
	 * mapper.
	 * 
	 * @param postRepository the repository for managing posts
	 * @param userRepository the repository for managing users
	 * @param postMapper     the mapper to convert between {@link Post} and
	 *                       {@link PostDto}
	 */
	public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postMapper = postMapper;
	}

	/**
	 * Retrieves a post by its unique identifier.
	 * 
	 * @param id the unique identifier of the post
	 * @return a {@link PostDto} representing the requested post
	 * @throws NotFoundException if no post is found with the given id
	 */
	public PostDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException());
		return postMapper.toDto(post);
	}

	/**
	 * Creates a new post with the provided data.
	 * 
	 * @param postDto the data transfer object containing the post details
	 * @return a {@link PostDto} representing the created post
	 */
	public PostDto createPost(PostDto postDto) {
		Post post = postRepository.save(postMapper.toEntity(postDto));
		return postMapper.toDto(post);
	}

	/**
	 * Retrieves all posts that the specified user is subscribed to.
	 * 
	 * @param userId the unique identifier of the user
	 * @return a list of {@link PostDto} representing the posts subscribed by the
	 *         user
	 * @throws NotFoundException if no user is found with the given id
	 */
	public List<PostDto> getSubscribedPosts(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());
		List<Topic> subscribedTopics = user.getSubscriptions();
		List<Post> subscribedPosts = postRepository.findByTopicIn(subscribedTopics);
		return postMapper.toDto(subscribedPosts);
	}
}
