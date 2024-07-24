package com.openclassrooms.mddapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.MappingService;

/**
 * Converts Post entity to PostDto and vice versa
 */
@Mapper(componentModel = "spring", uses = {MappingService.class})
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    /**
     * Converts a Post entity to a PostDto
     * @param post
     * @return PostDto
     */
    @Mapping(target = "topicId", source = "topic.id")
    @Mapping(target = "authorId", source = "user.id")
    PostDto toDto(Post post);

    /**
     * Converts a PostDto to a Post entity
     * @param postDto
     * @return Post
     */
    @Mapping(target = "topic", source = "topicId", qualifiedByName = "topicIdToTopicEntity")
    @Mapping(target = "user", source = "authorId", qualifiedByName = "userIdToUserEntity")
    Post toEntity(PostDto postDto);

    /**
     * Converts a list a Posts to a list of PostDtos
     * @param posts
     * @return
     */
    List<PostDto> toDto(List<Post> posts);

}
