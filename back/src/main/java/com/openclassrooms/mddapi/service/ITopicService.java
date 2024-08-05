package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.model.Topic;

/**
 * Service interface for managing topics.
 * <p>
 * This interface defines the operations related to {@link Topic} entities,
 * primarily focused on retrieving the list of available topics.
 * </p>
 * <p>
 * Implementations of this interface should provide the necessary business logic
 * and data access operations to manage the lifecycle of topics within the
 * application.
 * </p>
 * 
 * @see Topic
 * @version 1.0
 * @since 2024-07-22
 */
public interface ITopicService {

    /**
     * Retrieves a list of all topics.
     * <p>
     * This method returns a list of {@link Topic} objects representing all the
     * available
     * topics in the system.
     * </p>
     * 
     * @return a list of {@link Topic} objects representing the available topics
     */
    List<Topic> getTopics();

    /**
     * Retrieves a specific topic by its ID.
     * <p>
     * This method returns the {@link Topic} object that corresponds to the given
     * ID.
     * If the topic is not found, the method may throw an exception or return null,
     * depending on the implementation.
     * </p>
     * 
     * @param id the ID of the topic to retrieve
     * @return the {@link Topic} object with the specified ID
     */
    Topic getTopic(Long id);
}
