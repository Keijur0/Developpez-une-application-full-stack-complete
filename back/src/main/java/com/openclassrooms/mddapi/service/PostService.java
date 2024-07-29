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
 * Service for managing posts in the app.
 * 
 * Provides methods to retrieve and create posts.
 * Interacts with PostRepository.
 */

@Service
public class PostService implements IPostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	private final PostMapper postMapper;
	
	public PostService(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.postMapper = postMapper;
	}

	/**
	 * Retrieves a post by id
	 * @param id
	 * @return
	 */
	public PostDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException());
		return postMapper.toDto(post);
	}

	/**
	 * Creates a post
	 * @param post
	 * @return PostDto
	 */
	public PostDto createPost(PostDto postDto) {
		Post post = postRepository.save(postMapper.toEntity(postDto));
		return postMapper.toDto(post);
	}

	/**
	 * Retrieves all posts subscribed by a user
	 * @param userId
	 * @return List<PostDto>
	 */
	public List<PostDto> getSubscribedPosts(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());
		List<Topic> subscribedTopics = user.getSubscriptions();
		List<Post> subscribedPosts = postRepository.findByTopicIn(subscribedTopics);
		return postMapper.toDto(subscribedPosts);
	}
	
}
