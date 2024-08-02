package com.openclassrooms.mddapi.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;

@DisplayName("Topic controller unit tests")
public class TopicControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ITopicService topicService;

    @InjectMocks
    private TopicController topicController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }

    @DisplayName("Get topics - Success")
    @Test
    public void testGetTopics_Success() throws Exception {
        // Créez des objets Topic pour le test
        Topic topic1 = new Topic();
        topic1.setId(1L);
        topic1.setName("Topic 1");

        Topic topic2 = new Topic();
        topic2.setId(2L);
        topic2.setName("Topic 2");

        // Simulez le comportement du service
        when(topicService.getTopics()).thenReturn(List.of(topic1, topic2));

        // Effectuez la requête et vérifiez la réponse
        mockMvc.perform(get("/api/topic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(topic1, topic2))));
    }
}
