package com.openclassrooms.mddapi.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

class UserMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDtoWithSubscriptions() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("test!1234");

        Topic topic1 = new Topic();
        topic1.setId(101L);
        Topic topic2 = new Topic();
        topic2.setId(102L);

        user.setSubscriptions(Arrays.asList(topic1, topic2));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Act
        UserDto userDto = userMapper.toDto(user);

        // Assert
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("test", userDto.getUsername());
        assertEquals("test@test.com", userDto.getEmail());
        assertEquals(Arrays.asList(101L, 102L), userDto.getSubscriptionsId());
    }

    @Test
    void testToDtoWithNullSubscriptions() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("test!1234");
        user.setSubscriptions(null);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Act
        UserDto userDto = userMapper.toDto(user);

        // Assert
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("test", userDto.getUsername());
        assertEquals("test@test.com", userDto.getEmail());
        assertTrue(userDto.getSubscriptionsId().isEmpty());
    }

    @Test
    void testToEntity() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("test");
        userDto.setEmail("test@test.com");
        userDto.setSubscriptionsId(Arrays.asList(101L, 102L));

        // Act
        User user = userMapper.toEntity(userDto);

        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertNull(user.getCreatedAt()); // Ignored field
        assertNull(user.getUpdatedAt()); // Ignored field
        assertNull(user.getPassword());  // Ignored field
        assertNull(user.getSubscriptions()); // Ignored field
    }

    @Test
    void testToEntityWithEmptySubscriptions() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("test");
        userDto.setEmail("test@test.com");
        userDto.setSubscriptionsId(Collections.emptyList());

        // Act
        User user = userMapper.toEntity(userDto);

        // Assert
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertNull(user.getCreatedAt()); // Ignored field
        assertNull(user.getUpdatedAt()); // Ignored field
        assertNull(user.getPassword());  // Ignored field
        assertNull(user.getSubscriptions()); // Ignored field
    }
}
