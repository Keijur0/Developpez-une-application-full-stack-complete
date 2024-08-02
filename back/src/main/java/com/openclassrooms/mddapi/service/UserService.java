package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
 * 
 * Provides methods to retrieve and update users.
 * Interacts with UserRepository.
 */
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    private final UserDetailsServiceImpl userDetailsService;

    public UserService(UserRepository userRepository, TopicRepository topicRepository, UserMapper userMapper,
            JwtService jwtService, AuthenticationManager authManager, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Retrives user by id
     * 
     * @param id of the user to be retrieved.
     * @return user or throws exception if user doesn't exist.
     */
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return userMapper.toDto(user);
    }

    /**
     * Updates user with id, from UserDto
     * 
     * @param id
     * @param userDto
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

    public List<Topic> getUserSubscriptions(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return user.getSubscriptions();
    }

    /**
     * Adds user's subscription to a topic
     * 
     * @param userId
     * @param topicId
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
     * Cancels user's subscription to a topic
     * 
     * @param userId
     * @param topicId
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
