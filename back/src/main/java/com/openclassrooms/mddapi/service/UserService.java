package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

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

    public UserService(UserRepository userRepository, TopicRepository topicRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.userMapper = userMapper;
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
     * @param id
     * @param userDto
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<Topic> getUserSubscriptions(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return user.getSubscriptions();
    }

    /**
     * Adds user's subscription to a topic
     * @param userId
     * @param topicId
     */
    public void subscribe(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException());

        boolean alreadySubscribed = user.getSubscriptions().stream().anyMatch(t -> t.getId().equals(topicId));
        if(alreadySubscribed) {
            throw new BadRequestException();
        }

        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    /**
     * Cancels user's subscription to a topic
     * @param userId
     * @param topicId
     */
    public void unsubscribe(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException());

        boolean alreadySubscribed = user.getSubscriptions().stream().anyMatch(t -> t.getId().equals(topicId));
        if(!alreadySubscribed) {
            throw new BadRequestException();
        }

        user.setSubscriptions(user.getSubscriptions().stream().filter(topic -> !topic.getId().equals(topicId)).collect(Collectors.toList()));

    }
}
