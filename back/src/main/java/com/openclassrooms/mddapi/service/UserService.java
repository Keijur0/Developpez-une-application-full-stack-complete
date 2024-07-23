package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service for managing users in the app.
 * 
 * Provides methods to retrieve and update users.
 * Interacts with UserRepository.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrives user by id
     * 
     * @param id of the user to be retrieved.
     * @return user or throws exception if user doesn't exist.
     */
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        return userDto;
    }

    /**
     * Updates user with id, from UserDto
     * @param id
     * @param userDto
     */
    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
    }
}
