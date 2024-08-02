package com.openclassrooms.mddapi.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.service.IPostService;

@DisplayName("Post controller unit tests")
public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IPostService postService;

    @InjectMocks
    private PostController postController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @DisplayName("Get post by id - Success")
    @Test
    public void testGetPost_Success() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Test Post");

        when(postService.getPost(anyLong())).thenReturn(postDto);

        mockMvc.perform(get("/api/post/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postDto)));
    }

    @DisplayName("Get subscribed posts - Success")
    @Test
    public void testGetSubscribedPosts_Success() throws Exception {
        PostDto postDto1 = new PostDto();
        postDto1.setId(1L);
        postDto1.setTitle("Subscribed Post 1");

        PostDto postDto2 = new PostDto();
        postDto2.setId(2L);
        postDto2.setTitle("Subscribed Post 2");

        when(postService.getSubscribedPosts(anyLong())).thenReturn(List.of(postDto1, postDto2));

        mockMvc.perform(get("/api/post/subscribed/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(postDto1, postDto2))));
    }

    @DisplayName("Create post - success")
    @Test
    public void testCreatePost_Success() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("New Post");

        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);

        mockMvc.perform(post("/api/post")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postDto)));
    }
}
