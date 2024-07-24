package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.PostDto;
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

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Retrieves a post by id
	 * @param id
	 * @return
	 */
	public PostDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow();
		PostDto postDto = PostMapper.INSTANCE.toDto(post);
		return postDto;
	}

	/**
	 * Creates a post
	 * @param post
	 * @return
	 */
	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	/**
	 * Retrieves all posts subscribed by a user
	 * @param userId
	 * @return
	 */
	public List<Post> getSubscribedPosts(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		List<Topic> subscribedTopics = user.getSubscriptions();
		return postRepository.findByTopicIn(subscribedTopics);
	}
	
}
