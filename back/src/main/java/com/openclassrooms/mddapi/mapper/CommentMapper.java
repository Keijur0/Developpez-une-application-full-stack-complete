package com.openclassrooms.mddapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
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
     * <p>
     * This method maps the {@link Comment} entity to a {@link CommentDto}, converting the
     * associated {@link User} and {@link Post} entities to their respective IDs.
     * </p>
     * 
     * @param comment the Comment entity to convert
     * @return the corresponding CommentDto
     */
    @Mappings({
        @Mapping(source = "user.id", target = "userId"),
        @Mapping(source = "post.id", target = "postId")
    })
    public abstract CommentDto toDto(Comment comment);

    /**
     * Converts a CommentDto to a Comment entity.
     * <p>
     * This method maps a {@link CommentDto} to a {@link Comment} entity, resolving the user and post
     * references using their IDs. It also ignores the `createdAt` field during the conversion.
     * </p>
     * 
     * @param commentDto the CommentDto to convert
     * @return the corresponding Comment entity
     */
    @Mappings({
        @Mapping(target = "user", source = "userId", qualifiedByName = "toUserEntity"),
        @Mapping(target = "post", source = "postId", qualifiedByName = "toPostEntity"),
        @Mapping(target = "createdAt", ignore = true)
    })
    public abstract Comment toEntity(CommentDto commentDto);

    /**
     * Converts a list of Comment entities to a list of CommentDto objects.
     * <p>
     * This method iterates through a list of {@link Comment} entities and converts each one to a
     * {@link CommentDto}.
     * </p>
     * 
     * @param commentList the list of Comment entities to convert
     * @return the corresponding list of CommentDto objects
     */
    public abstract List<CommentDto> toDtos(List<Comment> commentList);

    /**
     * Converts a user ID to a User entity.
     * <p>
     * This method finds a {@link User} entity by its ID using the {@link UserRepository}.
     * If the user is not found, a {@link NotFoundException} is thrown.
     * </p>
     * 
     * @param id the ID of the user
     * @return the corresponding User entity
     * @throws NotFoundException if the user is not found
     */
    @Named("toUserEntity")
    User toUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    /**
     * Converts a post ID to a Post entity.
     * <p>
     * This method finds a {@link Post} entity by its ID using the {@link PostRepository}.
     * If the post is not found, a {@link NotFoundException} is thrown.
     * </p>
     * 
     * @param id the ID of the post
     * @return the corresponding Post entity
     * @throws NotFoundException if the post is not found
     */
    @Named("toPostEntity")
    Post toPostEntity(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }
}
