package com.openclassrooms.mddapi.service;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Component
public class MappingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Named("userIdToUserEntity")
    public User toUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Named("topicIdToTopicEntity")
    public Topic toTopicEntity(Long id) {
        return topicRepository.findById(id).orElseThrow();
    }
}
