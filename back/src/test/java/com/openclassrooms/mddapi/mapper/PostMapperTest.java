package com.openclassrooms.mddapi.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@DisplayName("Post mapper unit tests")
public class PostMapperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private PostMapperImpl postMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("To post dto - Success")
    @Test
    public void testToDto_Success() {
        User user = new User();
        user.setId(1L);

        Topic topic = new Topic();
        topic.setId(1L);

        Post post = new Post();
        post.setUser(user);
        post.setTopic(topic);

        PostDto postDto = postMapper.toDto(post);

        assertNotNull(postDto);
        assertEquals(1L, postDto.getAuthorId());
        assertEquals(1L, postDto.getTopicId());
    }

    @DisplayName("To post entity - Success")
    @Test
    public void testToEntity_Success() {
        PostDto postDto = new PostDto();
        postDto.setAuthorId(1L);
        postDto.setTopicId(1L);

        User user = new User();
        user.setId(1L);

        Topic topic = new Topic();
        topic.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        Post post = postMapper.toEntity(postDto);

        assertNotNull(post);
        assertEquals(user, post.getUser());
        assertEquals(topic, post.getTopic());
    }

    @DisplayName("To post entity Failure: User and Topic not found")
    @Test
    public void testToEntity_UserAndTopicNotFound() {
        PostDto postDto = new PostDto();
        postDto.setAuthorId(1L);
        postDto.setTopicId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        Post post = postMapper.toEntity(postDto);

        assertNotNull(post);
        assertNull(post.getUser()); // Le user n'existe pas, donc il devrait être null
        assertNull(post.getTopic()); // Le topic n'existe pas, donc il devrait être null
    }

    @DisplayName("To post dto list - Success")
    @Test
    public void testToDtoList_Success() {
        User user = new User();
        user.setId(1L);

        Topic topic = new Topic();
        topic.setId(1L);

        Post post1 = new Post();
        post1.setUser(user);
        post1.setTopic(topic);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setTopic(topic);

        List<Post> posts = Arrays.asList(post1, post2);
        List<PostDto> postDtos = postMapper.toDto(posts);

        assertNotNull(postDtos);
        assertEquals(2, postDtos.size());
        assertEquals(1L, postDtos.get(0).getAuthorId());
        assertEquals(1L, postDtos.get(0).getTopicId());
    }

    @Test
    public void testToDtoListEmpty() {
        List<Post> posts = Collections.emptyList();
        List<PostDto> postDtos = postMapper.toDto(posts);

        assertNotNull(postDtos);
        assertTrue(postDtos.isEmpty());
    }
}
