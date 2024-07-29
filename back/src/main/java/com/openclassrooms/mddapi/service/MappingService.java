package com.openclassrooms.mddapi.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service component for mapping IDs to entities.
 * 
 * This service provides methods for converting entity IDs to their corresponding entities.
 * It is used to ensure that references to {@link User}, {@link Topic}, and {@link Post} entities
 * are correctly resolved from their unique identifiers.
 * 
 * <p>The methods in this service are intended to be used with mapping frameworks such as MapStruct,
 * which can utilize the {@link Named} annotation to invoke these methods during the mapping process.</p>
 * 
 * @see User
 * @see Topic
 * @see Post
 * @see UserRepository
 * @see TopicRepository
 * @see PostRepository
 */
@Component
public class MappingService {

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final PostRepository postRepository;

    public MappingService(UserRepository userRepository, TopicRepository topicRepository,
            PostRepository postRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
    }

    /**
     * Converts a user ID to a {@link User} entity.
     * 
     * @param id the unique identifier of the user
     * @return the {@link User} entity associated with the given ID
     * @throws NotFoundException if no user with the specified ID is found
     */
    @Named("userIdToUserEntity")
    public User toUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    /**
     * Converts a topic ID to a {@link Topic} entity.
     * 
     * @param id the unique identifier of the topic
     * @return the {@link Topic} entity associated with the given ID
     * @throws NotFoundException if no topic with the specified ID is found
     */
    @Named("topicIdToTopicEntity")
    public Topic toTopicEntity(Long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    /**
     * Converts a post ID to a {@link Post} entity.
     * 
     * @param id the unique identifier of the post
     * @return the {@link Post} entity associated with the given ID
     * @throws NotFoundException if no post with the specified ID is found
     */
    @Named("postIdToPostEntity")
    public Post toPostEntity(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @Named("topicsToTopicIds")
    public List<Long> toTopicEntityList(List<Topic> topicList) {
        if (topicList == null) {
            return Collections.emptyList();
        }
        return topicList.stream()
                        .map(t -> t.getId())
                        .collect(Collectors.toList());
    }

}
