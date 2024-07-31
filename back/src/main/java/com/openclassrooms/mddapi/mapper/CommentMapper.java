package com.openclassrooms.mddapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * CommentMapper is an abstract class that provides mapping methods to convert 
 * between Comment entities and CommentDto objects. It utilizes MapStruct 
 * for the mapping process. The class also provides methods for converting lists 
 * of Comment entities to lists of CommentDto objects.
 * 
 * This class is annotated with @Mapper(componentModel = "spring") to indicate that 
 * it should be managed as a Spring component. The implementation of this mapper will be 
 * generated automatically by MapStruct at build time.
 */
@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    /**
     * Converts a Comment entity to a CommentDto.
     * 
     * @param comment the Comment entity to convert.
     * @return the corresponding CommentDto.
     */
    @Mappings({
        @Mapping(source = "user.id", target = "userId"),
        @Mapping(source = "post.id", target = "postId")
    })
    public abstract CommentDto toDto(Comment comment);

    /**
     * Converts a CommentDto to a Comment entity.
     * 
     * @param commentDto the CommentDto to convert.
     * @return the corresponding Comment entity.
     */
    @Mappings({
        @Mapping(target = "user", expression = "java(userRepository.findById(commentDto.getUserId()).orElse(null))"),
        @Mapping(target = "post", expression = "java(postRepository.findById(commentDto.getPostId()).orElse(null))"),
        @Mapping(target = "createdAt", ignore = true)
    })
    public abstract Comment toEntity(CommentDto commentDto);

    /**
     * Converts a list of Comment entities to a list of CommentDto objects.
     * 
     * @param commentList the list of Comment entities to convert.
     * @return the corresponding list of CommentDto objects.
     */
    public abstract List<CommentDto> toDtos(List<Comment> commentList);
}
