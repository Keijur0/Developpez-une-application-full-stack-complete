package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@DisplayName("Post service unit tests")
public class TopicServiceTest {

    @InjectMocks
    private TopicService topicService;

    @Mock
    private TopicRepository topicRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Get topics")
    @Test
    public void testGetTopics() {
        Topic topic1 = new Topic();
        Topic topic2 = new Topic();
        List<Topic> topics = List.of(topic1, topic2);
        when(topicRepository.findAll()).thenReturn(topics);

        List<Topic> result = topicService.getTopics();

        assertEquals(topics, result);
    }

    @DisplayName("Get topic by ID - Success")
    @Test
    public void testGetTopic_Success() {
        Long topicId = 1L;
        Topic topic = new Topic();
        topic.setId(topicId);
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        Topic result = topicService.getTopic(topicId);

        assertEquals(topic, result);
    }

    @DisplayName("Get topic by ID - Not Found")
    @Test
    public void testGetTopic_NotFound() {
        Long topicId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> topicService.getTopic(topicId));
    }
}
