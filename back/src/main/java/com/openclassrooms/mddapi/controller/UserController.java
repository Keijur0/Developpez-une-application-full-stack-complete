package com.openclassrooms.mddapi.controller;

import javax.validation.Valid;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing users.
 * This controller provides endpoints to handle requests related to {@link User}
 * entities,
 * including retrieving user details, updating user information, and managing
 * user subscriptions to topics.
 * 
 * <p>
 * All operations return a {@link ResponseEntity} to encapsulate the HTTP status
 * code and response body.
 * </p>
 * 
 * @see UserDto
 * @see IUserService
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "User endpoints")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves user details by user ID.
     * 
     * @param id the unique identifier of the user
     * @return a {@link ResponseEntity} containing the {@link UserDto} of the
     *         requested user
     */
    @Operation(description = "This endpoint is used to get a user by id and returns the user if it exists.", summary = "Gets a user.", responses = {
            @ApiResponse(description = "Success: Returns the user.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The user does not exist in the database.", responseCode = "404")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    /**
     * Updates the details of a user.
     * 
     * @param id      the unique identifier of the user
     * @param userDto the data transfer object containing the updated user details
     * @return a {@link ResponseEntity} indicating the outcome of the update
     *         operation
     */
    @Operation(description = "This endpoint is used to update a user with the information provided in the form. It updates the user information, renews his token and returns the new user information.", summary = "Updates the user and renews his token.", responses = {
            @ApiResponse(description = "Success: Updates the user, renews his token and returns his new information.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The user does not exist in the database.", responseCode = "404")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    /**
     * Retrieves the topics subscribed to by the user.
     * 
     * @param id the unique identifier of the user
     * @return a {@link ResponseEntity} containing a list of topics the user is
     *         subscribed to
     */
    @Operation(description = "This endpoint is used to get all the topics the user has subscribed and returns the list of topics.", summary = "Gets all subscribed topics.", responses = {
            @ApiResponse(description = "Success: Returns the list of subscribed topics.", responseCode = "200"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The user does not exist in the database.", responseCode = "404")
    })
    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<?> getUserSubscriptions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserSubscriptions(id));
    }

    /**
     * Subscribes a user to a topic.
     * 
     * @param id      the unique identifier of the user
     * @param topicId the unique identifier of the topic to subscribe to
     * @return a {@link ResponseEntity} indicating the outcome of the subscription
     *         operation
     */
    @Operation(description = "This endpoint is used to subscribe a user to a topic.", summary = "Subscribes the user to a topic.", responses = {
            @ApiResponse(description = "Success: Subscribes the user to the topic.", responseCode = "200"),
            @ApiResponse(description = "Bad request: The user has already subscribed to this topic", responseCode = "400"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The user or the topic does not exist in the database.", responseCode = "404")
    })
    @PutMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long id, @PathVariable("topicId") Long topicId) {
        userService.subscribe(id, topicId);
        return ResponseEntity.ok().build();
    }

    /**
     * Unsubscribes a user from a topic.
     * 
     * @param id      the unique identifier of the user
     * @param topicId the unique identifier of the topic to unsubscribe from
     * @return a {@link ResponseEntity} indicating the outcome of the unsubscription
     *         operation
     */
    @Operation(description = "This endpoint is used to unsubscribe a user from a topic.", summary = "Unsubscribes the user from a topic.", responses = {
            @ApiResponse(description = "Success: Unsubscribes the user from the topic.", responseCode = "200"),
            @ApiResponse(description = "Bad request: The user is not subscribed to this topic", responseCode = "400"),
            @ApiResponse(description = "Unauthorized: The request is missing a valid token", responseCode = "401"),
            @ApiResponse(description = "Not found: The user or the topic does not exist in the database.", responseCode = "404")
    })
    @DeleteMapping("/{id}/subscribe/{topicId}")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") Long id, @PathVariable("topicId") Long topicId) {
        userService.unsubscribe(id, topicId);
        return ResponseEntity.ok().build();
    }
}
