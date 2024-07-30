package com.openclassrooms.mddapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.MappingService;

/**
 * CommentMapper is an interface used to convert Comment entities to CommentDto objects and vice versa.
 * It utilizes MapStruct for the mapping process and includes custom mapping services for complex conversions.
 */
@Mapper(componentModel = "spring", uses = {MappingService.class})
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    /**
     * Converts a Comment entity to a CommentDto.
     *
     * @param comment the Comment entity to convert.
     * @return the corresponding CommentDto.
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "postId", source = "post.id")
    CommentDto toDto(Comment comment);

    /**
     * Converts a CommentDto to a Comment entity.
     *
     * @param commentDto the CommentDto to convert.
     * @return the corresponding Comment entity.
     */
    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUserEntity")
    @Mapping(target = "post", source = "postId", qualifiedByName = "postIdToPostEntity")
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto commentDto);

    /**
     * Converts a list of Comment entities to a list of CommentDto.
     *
     * @param commentList the list of Comment entities to convert.
     * @return the corresponding list of CommentDto.
     */
    List<CommentDto> toDto(List<Comment> commentList);
}
