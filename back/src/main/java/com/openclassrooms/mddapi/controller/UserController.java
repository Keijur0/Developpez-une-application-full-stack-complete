package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.IUserService;

/**
 * REST controller for managing users.
 * This controller provides endpoints to handle requests related to {@link User} entities,
 * including retrieving user details, updating user information, and managing user subscriptions to topics.
 * 
 * <p>All operations return a {@link ResponseEntity} to encapsulate the HTTP status code and response body.</p>
 * 
 * @see UserDto
 * @see IUserService
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * Retrieves user details by user ID.
     * 
     * @param id the unique identifier of the user
     * @return a {@link ResponseEntity} containing the {@link UserDto} of the requested user
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * Updates the details of a user.
     * 
     * @param id the unique identifier of the user
     * @param userDto the data transfer object containing the updated user details
     * @return a {@link ResponseEntity} indicating the outcome of the update operation
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    /**
     * Retrieves the topics subscribed to by the user.
     * 
     * @param id the unique identifier of the user
     * @return a {@link ResponseEntity} containing a list of topics the user is subscribed to
     */
    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserSubscriptions(id));
    }

    /**
     * Subscribes a user to a topic.
     * 
     * @param id the unique identifier of the user
     * @param topicId the unique identifier of the topic to subscribe to
     * @return a {@link ResponseEntity} indicating the outcome of the subscription operation
     */
    @PutMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long id, @PathVariable("topicId") Long topicId) {
        userService.subscribe(id, topicId);
        return ResponseEntity.ok().build();
    }

    /**
     * Unsubscribes a user from a topic.
     * 
     * @param id the unique identifier of the user
     * @param topicId the unique identifier of the topic to unsubscribe from
     * @return a {@link ResponseEntity} indicating the outcome of the unsubscription operation
     */
    @DeleteMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long id, @PathVariable("topicId") Long topicId) {
        userService.unsubscribe(id, topicId);
        return ResponseEntity.ok().build();
    }
}
