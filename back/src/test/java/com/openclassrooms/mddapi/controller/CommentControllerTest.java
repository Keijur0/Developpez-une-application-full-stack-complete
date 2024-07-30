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
import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.service.ICommentService;

@DisplayName("Comment controller unit tests")
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @DisplayName("Get comments test - Success")
    @Test
    public void testGetComments_Success() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setMessage("Test Comment");

        when(commentService.getComments(anyLong())).thenReturn(List.of(commentDto));

        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(commentDto))));
    }

    @DisplayName("Create comment test - Success")
    @Test
    public void testCreateComment_Success() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setMessage("Test Comment");

        when(commentService.createComment(any(CommentDto.class))).thenReturn(commentDto);

        mockMvc.perform(post("/api/comment/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(commentDto)));
    }
}
