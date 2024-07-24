package com.openclassrooms.mddapi.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    /**
     * Retrives user by id
     * 
     * @param id of the user to be retrieved.
     * @return user or throws exception if user doesn't exist.
     */
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        return UserMapper.INSTANCE.toDto(user);
    }

    /**
     * Updates user with id, from UserDto
     * @param id
     * @param userDto
     */
    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
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
