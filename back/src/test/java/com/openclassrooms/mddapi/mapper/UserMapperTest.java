package com.openclassrooms.mddapi.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

@DisplayName("User mapper unit tests")
public class UserMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("To user dto")
    @Test
    public void testToDto_SubscriptionsNotNull() {
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

        UserDto userDto = userMapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("test", userDto.getUsername());
        assertEquals("test@test.com", userDto.getEmail());
    }

    @DisplayName("To user entity")
    @Test
    public void testToEntity_SubscriptionsNotNull() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("test");
        userDto.setEmail("test@test.com");

        User user = userMapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
        assertNull(user.getPassword());
        assertNull(user.getSubscriptions());
    }
}
