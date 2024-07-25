package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Topic;

/**
 * Service interface for managing users.
 * This interface defines the operations that can be performed on {@link UserDto} objects,
 * including retrieving user details, updating user information, and managing user subscriptions to topics.
 * 
 * <p>Implementations of this interface are responsible for the business logic
 * and data layer interactions related to user management.</p>
 * 
 * @see UserDto
 */
public interface IUserService {

    /**
     * Retrieves a user by their unique identifier.
     * 
     * @param id the id of the user
     * @return the {@link UserDto} corresponding to the id
     */
    UserDto getUser(Long id);

    /**
     * Updates the information of a user.
     * 
     * @param id the id of the user to update
     * @param userDto the data transfer object containing the updated details of the user
     * @return 
     */
    UserDto updateUser(Long id, UserDto userDto);

    /**
     * Retrieves a user's subscribed topics.
     * @param id the unique identifier of the user
     * @return the list of {@link Topic} the user is subscribed to
     */
    List<Topic> getUserSubscriptions(Long id);

    /**
     * Subscribes a user to a specific topic.
     * 
     * @param userId the unique identifier of the user
     * @param topicId the unique identifier of the topic to subscribe to
     */
    void subscribe(Long userId, Long topicId);

    /**
     * Unsubscribes a user from a specific topic.
     * 
     * @param userId the unique identifier of the user
     * @param topicId the unique identifier of the topic to unsubscribe from
     */
    void unsubscribe(Long userId, Long topicId);
}
