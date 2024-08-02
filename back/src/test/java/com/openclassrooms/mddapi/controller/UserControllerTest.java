package com.openclassrooms.mddapi.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IUserService;

@DisplayName("User controller unit tests")
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setUsername("test");
        userDto.setSubscriptionsId(Collections.emptyList());

        when(userService.getUser(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("updated@test.com");
        userDto.setUsername("updateduser");
        userDto.setSubscriptionsId(Collections.emptyList());

        when(userService.updateUser(eq(1L), any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void testGetUserSubscriptions() throws Exception {
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        topic1.setId(1L);
        topic2.setId(2L);
        List<Topic> topics = List.of(topic1, topic2);

        User user = new User();
        user.setId(1L);
        user.setSubscriptions(topics);

        when(userService.getUserSubscriptions(1L)).thenReturn(topics);

        mockMvc.perform(get("/api/user/1/subscriptions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(topics)));
    }

    @Test
    public void testSubscribe() throws Exception {
        doNothing().when(userService).subscribe(eq(1L), eq(2L));

        mockMvc.perform(put("/api/user/1/subscribe/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnsubscribe() throws Exception {
        doNothing().when(userService).unsubscribe(eq(1L), eq(2L));

        mockMvc.perform(delete("/api/user/1/subscribe/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
