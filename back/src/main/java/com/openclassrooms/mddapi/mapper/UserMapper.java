package com.openclassrooms.mddapi.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

/**
 * Provides methods to map between {@link User} entities and {@link UserDto} objects.
 * <p>
 * This mapper uses MapStruct to automatically generate the implementation. It converts
 * a {@link User} entity to a {@link UserDto} and vice versa. The mappings are defined to
 * handle specific fields and transformations.
 * </p>
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     * <p>
     * This method maps the {@link User} entity to a {@link UserDto} while converting the user's
     * subscriptions to a list of subscription IDs. If the subscriptions are null, an empty list
     * is used instead.
     * </p>
     * 
     * @param user the {@link User} entity to convert
     * @return the corresponding {@link UserDto}
     */
    @Mapping(target = "subscriptionsId", source="subscriptions", qualifiedByName = "topicsToTopicIds")
    public abstract UserDto toDto(User user);

    /**
     * Converts a {@link UserDto} to a {@link User} entity.
     * <p> 
     * This method maps the {@link UserDto} to a {@link User} entity. Certain fields are ignored during
     * the mapping:
     * <ul>
     *     <li>{@code createdAt}: The creation timestamp is not set during the conversion.</li>
     *     <li>{@code updatedAt}: The last update timestamp is not set during the conversion.</li>
     *     <li>{@code password}: The password is not included in the conversion to avoid security issues.</li>
     *     <li>{@code subscriptions}: The subscriptions list is not set, as it is not included in the DTO.</li>
     * </ul>
     * </p>
     * 
     * @param userDto the {@link UserDto} to convert
     * @return the corresponding {@link User} entity
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    public abstract User toEntity(UserDto userDto);

    /**
     * Converts a list of {@link Topic} entities to a list of topic IDs.
     * <p>
     * This method is used to extract the IDs from a list of {@link Topic} entities.
     * If the provided list is null, it returns an empty list.
     * </p>
     * 
     * @param topicList the list of {@link Topic} entities
     * @return a list of topic IDs
     */
    @Named("topicsToTopicIds")
    List<Long> toTopicIdList(List<Topic> topicList) {
        if (topicList == null) {
            return Collections.emptyList();
        }
        return topicList.stream()
                        .map(Topic::getId)
                        .collect(Collectors.toList());
    }
}
