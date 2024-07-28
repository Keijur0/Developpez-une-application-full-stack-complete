package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.MappingService;

/**
 * Converts User entity to UserDto and vice versa
 */
@Mapper(componentModel = "spring", uses = {MappingService.class})
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts a User entity to a UserDto.
     * 
     * @param user
     * @return UserDto
     */
    @Mapping(target = "subscriptionsId",
            source = "subscriptions",
            qualifiedByName = "topicsToTopicIds")
    UserDto toDto(User user);

    /**
     * Converts a UserDto to a User entity.
     * @param userDto
     * @return the UserEntity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    User toEntity(UserDto userDto);
}
