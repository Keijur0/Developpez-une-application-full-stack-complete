package com.openclassrooms.mddapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * PostMapper is an abstract class that provides mapping methods to convert 
 * between Post entities and PostDto objects. It uses MapStruct for the mapping process.
 * The class also provides methods for converting lists of Post entities to lists of PostDto objects.
 * 
 * This class is annotated with @Mapper(componentModel = "spring") to indicate that 
 * it should be managed as a Spring component. The implementation of this mapper will be 
 * generated automatically by MapStruct at build time.
 */
@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TopicRepository topicRepository;

    /**
     * Converts a Post entity to a PostDto.
     * 
     * @param post the Post entity to convert.
     * @return the corresponding PostDto.
     */
    @Mapping(target = "topicId", source = "topic.id")
    @Mapping(target = "authorId", source = "user.id")
    public abstract PostDto toDto(Post post);

    /**
     * Converts a PostDto to a Post entity.
     * 
     * @param postDto the PostDto to convert.
     * @return the corresponding Post entity.
     * @throws NotFoundException if the associated user or topic is not found in the repositories.
     */
    @Mapping(target = "topic", expression = "java(topicRepository.findById(postDto.getTopicId()).orElse(null))")
    @Mapping(target = "user", expression = "java(userRepository.findById(postDto.getAuthorId()).orElse(null))")
    public abstract Post toEntity(PostDto postDto);

    /**
     * Converts a list of Post entities to a list of PostDto objects.
     * 
     * @param posts the list of Post entities to convert.
     * @return the corresponding list of PostDto objects.
     */
    public abstract List<PostDto> toDto(List<Post> posts);

}
