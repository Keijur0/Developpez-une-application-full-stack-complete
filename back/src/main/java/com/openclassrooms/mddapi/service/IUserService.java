package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Topic;

/**
 * Service interface for managing users.
 * <p>
 * This interface defines the operations that can be performed on
 * {@link UserDto} objects, including retrieving user details, updating user
 * information, and managing user subscriptions to topics.
 * </p>
 * <p>
 * Implementations of this interface are responsible for the business logic
 * and data layer interactions related to user management.
 * </p>
 * 
 * @see UserDto
 * @version 1.0
 * @since 2024-07-22
 */
public interface IUserService {

    /**
     * Retrieves a user by their unique identifier.
     * <p>
     * This method fetches the {@link UserDto} object associated with the specified
     * user ID.
     * </p>
     * 
     * @param id the id of the user to retrieve
     * @return the {@link UserDto} corresponding to the specified id
     */
    UserDto getUser(Long id);

    /**
     * Updates the information of a user.
     * <p>
     * This method updates the user's information based on the provided
     * {@link UserDto} object. The user to be updated is identified by the provided
     * id.
     * </p>
     * 
     * @param id      the id of the user to update
     * @param userDto the data transfer object containing the updated details of the
     *                user
     * @return a {@link ResponseEntity} indicating the result of the update
     *         operation
     */
    ResponseEntity<?> updateUser(Long id, UserDto userDto);

    /**
     * Retrieves a user's subscribed topics.
     * <p>
     * This method returns a list of {@link Topic} objects that the user with the
     * specified ID is subscribed to.
     * </p>
     * 
     * @param id the unique identifier of the user
     * @return a list of {@link Topic} objects representing the user's subscriptions
     */
    List<Topic> getUserSubscriptions(Long id);

    /**
     * Subscribes a user to a specific topic.
     * <p>
     * This method adds the user with the specified ID to the list of subscribers
     * for the topic with the given ID.
     * </p>
     * 
     * @param userId  the unique identifier of the user
     * @param topicId the unique identifier of the topic to subscribe to
     */
    void subscribe(Long userId, Long topicId);

    /**
     * Unsubscribes a user from a specific topic.
     * <p>
     * This method removes the user with the specified ID from the list of
     * subscribers for the topic with the given ID.
     * </p>
     * 
     * @param userId  the unique identifier of the user
     * @param topicId the unique identifier of the topic to unsubscribe from
     */
    void unsubscribe(Long userId, Long topicId);
}
