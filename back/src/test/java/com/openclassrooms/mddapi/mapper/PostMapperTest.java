package com.openclassrooms.mddapi.mapper;

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

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
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
        postMapper.userRepository = userRepository;
        postMapper.topicRepository = topicRepository;
    }

    @DisplayName("To post dto - Success")
    @Test
    public void testToDto_Success() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        User user = new User();
        user.setId(1L);
        post.setUser(user);

        Topic topic = new Topic();
        topic.setId(1L);
        post.setTopic(topic);

        PostDto postDto = postMapper.toDto(post);

        assertNotNull(postDto);
        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(post.getContent(), postDto.getContent());
        assertEquals(post.getUser().getId(), postDto.getAuthorId());
        assertEquals(post.getTopic().getId(), postDto.getTopicId());
    }

    @DisplayName("To post entity - Success")
    @Test
    public void testToEntity_Success() {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setAuthorId(1L);
        postDto.setTopicId(1L);

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Topic topic = new Topic();
        topic.setId(1L);
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        Post post = postMapper.toEntity(postDto);

        assertNotNull(post);
        assertEquals(postDto.getId(), post.getId());
        assertEquals(postDto.getTitle(), post.getTitle());
        assertEquals(postDto.getContent(), post.getContent());
        assertEquals(postDto.getAuthorId(), post.getUser().getId());
        assertEquals(postDto.getTopicId(), post.getTopic().getId());
    }

    @DisplayName("To post entity - Failure: User not found")
    @Test
    public void testToEntity_UserNotFound() {
        PostDto postDto = new PostDto();
        postDto.setAuthorId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postMapper.toEntity(postDto));
    }

    @DisplayName("To post entity - Failure: Post not found")
    @Test
    public void testToEntity_TopicNotFound() {
        PostDto postDto = new PostDto();
        postDto.setTopicId(1L);

        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postMapper.toEntity(postDto));
    }

    @DisplayName("To post dto list - Success")
    @Test
    public void testToDtoList_Success() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Title 1");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Title 2");

        List<Post> posts = List.of(post1, post2);
        List<PostDto> postDtos = postMapper.toDto(posts);

        assertNotNull(postDtos);
        assertEquals(2, postDtos.size());
        assertEquals(post1.getId(), postDtos.get(0).getId());
        assertEquals(post2.getId(), postDtos.get(1).getId());
    }
}
