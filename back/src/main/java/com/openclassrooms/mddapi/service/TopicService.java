package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private TopicRepository topicRepository;

	/**
	 * Retrieves all topics
	 */
	@Override
	public List<Topic> getTopics() {
		return topicRepository.findAll();
	}
	
}
