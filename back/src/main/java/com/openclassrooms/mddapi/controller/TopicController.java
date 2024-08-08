package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for managing topics.
 * This controller provides endpoints to handle requests related to
 * {@link Topic} entities.
 * 
 * @author Your Name
 */
@RestController
@RequestMapping("/api/topic")
@Tag(name = "Topic endpoints")
public class TopicController {

	private final ITopicService topicService;

	/**
	 * Constructs a new {@code TopicController} with the specified
	 * {@code ITopicService}.
	 *
	 * @param topicService the service used to manage topics
	 */
	public TopicController(ITopicService topicService) {
		this.topicService = topicService;
	}

	/**
	 * Retrieves a list of all topics.
	 * 
	 * @return a list of {@link Topic} objects
	 */
	@Operation(description = "This endpoint is used to get all the topics in the database and returns a list of all existing topics.", summary = "Gets all topics.", responses = {
			@ApiResponse(description = "Success: Returns the list of topics.", responseCode = "200"),
			@ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401")
	})
	@GetMapping
	public List<Topic> getTopics() {
		return topicService.getTopics();
	}

	/**
	 * Retrieves a topic by its ID.
	 *
	 * @param id the ID of the topic to retrieve
	 * @return the {@link Topic} with the specified ID
	 */
	@Operation(description = "This endpoint is used to get a topic by id and returns a topic if it exists.", summary = "Gets a topic by id.", responses = {
			@ApiResponse(description = "Success: Returns the topic.", responseCode = "200"),
			@ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
			@ApiResponse(description = "Not found: The topic does not exist in the database.", responseCode = "404")
	})
	@GetMapping("/{id}")
	public Topic getTopic(@PathVariable Long id) {
		return topicService.getTopic(id);
	}
}
