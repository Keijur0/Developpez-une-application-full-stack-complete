package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserDto;

public interface IUserService {

    UserDto getUser(Long id);

    void updateUser(Long id, UserDto userDto);

    void subscribe(Long userId, Long topicId);

    void unsubscribe(Long userId, Long topicId);
}
