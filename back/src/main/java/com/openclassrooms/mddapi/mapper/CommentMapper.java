package com.openclassrooms.mddapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.MappingService;

/**
 * Converts Comment entity to CommentDto and vice versa
 */
@Mapper(componentModel = "spring", uses = {MappingService.class})
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    /**
     * Converts a Comment entity to a CommentDto
     * @param comment
     * @return CommentDto
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "postId", source = "post.id")
    CommentDto toDto(Comment comment);

    /**
     * Converts a CommentDto to a Comment entity
     * @param commentDto
     * @return Comment
     */
    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUserEntity")
    @Mapping(target = "post", source = "postId", qualifiedByName = "postIdToPostEntity")
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto commentDto);

    /**
     * Converts a Comment entity list to a CommentDto list
     * @param commentList
     * @return List<CommentDto>
     */
    List<CommentDto> toDto(List<Comment> commentList);
}
