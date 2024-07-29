package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;

/**
 * Controller for managing topics.
 * This controller provides endpoints to handle requests related to {@link Topic} entities.
 * 
 * @author 
 */
@RestController
@RequestMapping("/api/topic")
public class TopicController {

	private final ITopicService topicService;

	public TopicController(ITopicService topicService) {
		this.topicService = topicService;
	}

	/**
     * Retrieves a list of all topics.
     * 
     * @return a list of {@link Topic} objects
     */
	@GetMapping
	public List<Topic> getTopics() {
		return topicService.getTopics();
	}
	
	
}
