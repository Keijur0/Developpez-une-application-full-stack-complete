package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@DisplayName("Mapping service unit tests")
public class MappingServiceTest {

    @InjectMocks
    private MappingService mappingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("To user entity - Success")
    @Test
    public void testToUserEntity_Success() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = mappingService.toUserEntity(userId);

        assertEquals(user, result);
    }

    @DisplayName("To user entity - Failure: User not found")
    @Test
    public void testToUserEntity_NotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> mappingService.toUserEntity(userId));
    }

    @DisplayName("To topic entity - Success")
    @Test
    public void testToTopicEntity_Success() {
        Long topicId = 1L;
        Topic topic = new Topic();
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        Topic result = mappingService.toTopicEntity(topicId);

        assertEquals(topic, result);
    }

    @DisplayName("To topic entity - Failure: Topic not found")
    @Test
    public void testToTopicEntity_NotFound() {
        Long topicId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> mappingService.toTopicEntity(topicId));
    }

    @DisplayName("To post entity - Success")
    @Test
    public void testToPostEntity_Success() {
        Long postId = 1L;
        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = mappingService.toPostEntity(postId);

        assertEquals(post, result);
    }

    @DisplayName("To post entity - Failure: Post not found")
    @Test
    public void testToPostEntity_NotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> mappingService.toPostEntity(postId));
    }

    @DisplayName("To topic id list")
    @Test
    public void testToTopicIdList() {
        Topic topic1 = new Topic();
        topic1.setId(1L);
        Topic topic2 = new Topic();
        topic2.setId(2L);
        List<Topic> topics = List.of(topic1, topic2);

        List<Long> result = mappingService.toTopicIdList(topics);

        assertEquals(List.of(1L, 2L), result);
    }

    @DisplayName("To topic id list - Empty list")
    @Test
    public void testToTopicIdList_EmptyList() {
        List<Topic> topics = Collections.emptyList();

        List<Long> result = mappingService.toTopicIdList(topics);

        assertEquals(Collections.emptyList(), result);
    }

    @DisplayName("To topic id list - Null list")
    @Test
    public void testToTopicIdList_NullList() {
        List<Topic> topics = null;

        List<Long> result = mappingService.toTopicIdList(topics);

        assertEquals(Collections.emptyList(), result);
    }
}
