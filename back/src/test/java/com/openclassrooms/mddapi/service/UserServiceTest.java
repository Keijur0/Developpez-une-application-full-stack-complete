package com.openclassrooms.mddapi.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@DisplayName("User service unit tests")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;
    private Topic topic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setUsername("test");
        user.setSubscriptions(new ArrayList<>());

        topic = new Topic();
        topic.setId(1L);

        userDto = new UserDto();
        userDto.setEmail("updated@test.com");
        userDto.setUsername("updateduser");
    }

    @DisplayName("Get user - Success")
    @Test
    public void testGetUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUser(1L);

        assertEquals(userDto, result);
    }

    @DisplayName("Get user - Failure: User not found")
    @Test
    public void testGetUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUser(1L));
    }

    @DisplayName("Update user - Success")
    @Test
    public void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.updateUser(1L, userDto);

        assertEquals(userDto, result);
        verify(userRepository).save(user);
    }

    @DisplayName("Update user - Failure: User not found")
    @Test
    public void testUpdateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, userDto));
    }

    @DisplayName("Get user subscriptions - Success")
    @Test
    public void testGetUserSubscriptions_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Topic> result = userService.getUserSubscriptions(1L);

        assertEquals(Collections.emptyList(), result);
    }

    @DisplayName("Get user subscriptions - Failure: User not found")
    @Test
    public void testGetUserSubscriptions_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserSubscriptions(1L));
    }

    @DisplayName("Subscribe - Success")
    @Test
    public void testSubscribe_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        userService.subscribe(1L, 1L);

        assertTrue(user.getSubscriptions().contains(topic));
        verify(userRepository).save(user);
    }

    @DisplayName("Subscribe - Failure: User not found")
    @Test
    public void testSubscribe_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.subscribe(1L, 1L));
    }

    @DisplayName("Subscribe - Failure: Topic not found")
    @Test
    public void testSubscribe_TopicNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.subscribe(1L, 1L));
    }

    @DisplayName("Subscribe - Failure: Already subscribed")
    @Test
    public void testSubscribe_AlreadySubscribed() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        user.getSubscriptions().add(topic);

        assertThrows(BadRequestException.class, () -> userService.subscribe(1L, 1L));
    }

    @DisplayName("Unsubscribe - Success")
    @Test
    public void testUnsubscribe_Success() {
        user.getSubscriptions().add(topic);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        userService.unsubscribe(1L, 1L);

        assertFalse(user.getSubscriptions().contains(topic));
        verify(userRepository).save(user);
    }

    @DisplayName("Unsubscribe - Failure: User not found")
    @Test
    public void testUnsubscribe_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.unsubscribe(1L, 1L));
    }

    @DisplayName("Unsubscribe - Failure: Topic not found")
    @Test
    public void testUnsubscribe_TopicNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.unsubscribe(1L, 1L));
    }

    @DisplayName("Unsubscribe - Failure: Not subscribed")
    @Test
    public void testUnsubscribe_NotSubscribed() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        assertThrows(BadRequestException.class, () -> userService.unsubscribe(1L, 1L));
    }
}
