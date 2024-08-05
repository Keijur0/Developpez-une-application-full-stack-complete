package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageReponse;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.service.JwtService;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.security.service.UserDetailsServiceImpl;

/**
 * Service for managing users in the app.
 * <p>
 * Provides methods to retrieve and update users.
 * Interacts with {@link UserRepository} and {@link TopicRepository}.
 * </p>
 */
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructs a new {@link UserService} with the specified dependencies.
     * 
     * @param userRepository     the repository to manage users
     * @param topicRepository    the repository to manage topics
     * @param userMapper         the mapper to convert between {@link User} and
     *                           {@link UserDto}
     * @param jwtService         the service to handle JWT operations
     * @param userDetailsService the service to load user details
     */
    public UserService(UserRepository userRepository, TopicRepository topicRepository, UserMapper userMapper,
            JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Retrieves a user by their unique identifier.
     * 
     * @param id the id of the user to be retrieved
     * @return the {@link UserDto} corresponding to the specified id
     * @throws NotFoundException if no user is found with the given id
     */
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return userMapper.toDto(user);
    }

    /**
     * Updates a user's information based on the provided {@link UserDto}.
     * 
     * @param id      the id of the user to update
     * @param userDto the data transfer object containing the updated user details
     * @return a {@link ResponseEntity} containing the updated {@link AuthResponse}
     * @throws NotFoundException   if no user is found with the given id
     * @throws BadRequestException if the email or username is already in use
     */
    public ResponseEntity<?> updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());

        if (userRepository.existsByEmail(userDto.getEmail()) && !userDto.getEmail().equalsIgnoreCase(user.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Email already in use"));
        }

        if (userRepository.existsByUsername(userDto.getUsername())
                && !userDto.getUsername().equalsIgnoreCase(user.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Username already in use"));
        }

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(
                token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    /**
     * Retrieves a list of topics that the user is subscribed to.
     * 
     * @param id the unique identifier of the user
     * @return a list of {@link Topic} objects representing the user's subscriptions
     * @throws NotFoundException if no user is found with the given id
     */
    public List<Topic> getUserSubscriptions(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return user.getSubscriptions();
    }

    /**
     * Subscribes a user to a specific topic.
     * 
     * @param userId  the unique identifier of the user
     * @param topicId the unique identifier of the topic to subscribe to
     * @throws NotFoundException   if the user or topic is not found
     * @throws BadRequestException if the user is already subscribed to the topic
     */
    public void subscribe(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException());

        boolean alreadySubscribed = user.getSubscriptions().stream().anyMatch(t -> t.getId().equals(topicId));
        if (alreadySubscribed) {
            throw new BadRequestException();
        }

        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    /**
     * Unsubscribes a user from a specific topic.
     * 
     * @param userId  the unique identifier of the user
     * @param topicId the unique identifier of the topic to unsubscribe from
     * @throws NotFoundException   if the user or topic is not found
     * @throws BadRequestException if the user is not subscribed to the topic
     */
    public void unsubscribe(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());
        topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException());

        boolean alreadySubscribed = user.getSubscriptions().stream().anyMatch(t -> t.getId().equals(topicId));
        if (!alreadySubscribed) {
            throw new BadRequestException();
        }

        user.setSubscriptions(
                user.getSubscriptions().stream().filter(t -> !t.getId().equals(topicId)).collect(Collectors.toList()));
        userRepository.save(user);
    }
}
