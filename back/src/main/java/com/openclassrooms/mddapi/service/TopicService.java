package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

/**
 * Service for managing topics in the app.
 * 
 * Provides methods to retrieve topics.
 * Interacts with TopicRepository.
 */
@Service
public class TopicService implements ITopicService {

	private final TopicRepository topicRepository;

	/**
	 * Constructs a new {@code TopicService} with the specified
	 * {@code TopicRepository}.
	 *
	 * @param topicRepository the repository used to manage topics
	 */
	public TopicService(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	/**
	 * Retrieves all topics.
	 *
	 * @return a list of {@link Topic} objects
	 */
	@Override
	public List<Topic> getTopics() {
		return topicRepository.findAll();
	}

	/**
	 * Retrieves a topic by its ID.
	 *
	 * @param id the ID of the topic to retrieve
	 * @return the {@link Topic} with the specified ID
	 * @throws NotFoundException if the topic with the specified ID is not found
	 */
	@Override
	public Topic getTopic(Long id) {
		return topicRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
}
