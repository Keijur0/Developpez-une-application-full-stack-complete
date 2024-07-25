package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.model.Topic;

/**
 * Service interface for managing topics.
 * This interface defines the operations related to {@link Topic} entities, primarily
 * focused on retrieving the list of available topics.
 * 
 * <p>Implementations of this interface should provide the necessary business logic and 
 * data access operations to manage the lifecycle of topics within the application.</p>
 * 
 * @see Topic
 */
public interface ITopicService {

    /**
     * Retrieves a list of all topics.
     * 
     * @return a list of {@link Topic} objects representing the available topics
     */
    List<Topic> getTopics();

}
