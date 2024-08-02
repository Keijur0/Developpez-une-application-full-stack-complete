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

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@DisplayName("Post service unit tests")
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Get post - Success")
    @Test
    public void testGetPost_Success() {
        Long postId = 1L;
        Post post = new Post();
        PostDto postDto = new PostDto();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postDto);

        PostDto result = postService.getPost(postId);

        assertEquals(postDto, result);
    }

    @DisplayName("Get post - Failure: Post not found")
    @Test
    public void testGetPost_NotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.getPost(postId));
    }

    @DisplayName("Create post")
    @Test
    public void testCreatePost() {
        PostDto postDto = new PostDto();
        Post post = new Post();
        PostDto createdPostDto = new PostDto();
        when(postMapper.toEntity(postDto)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(createdPostDto);

        PostDto result = postService.createPost(postDto);

        assertEquals(createdPostDto, result);
    }

    @DisplayName("Get subscribed posts - Success")
    @Test
    public void testGetSubscribedPosts_Success() {
        Long userId = 1L;
        User user = new User();
        Topic topic = new Topic();
        Post post = new Post();
        PostDto postDto = new PostDto();
        List<Topic> subscribedTopics = List.of(topic);
        List<Post> subscribedPosts = List.of(post);
        List<PostDto> postDtos = List.of(postDto);

        user.setSubscriptions(subscribedTopics);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findByTopicIn(subscribedTopics)).thenReturn(subscribedPosts);
        when(postMapper.toDto(subscribedPosts)).thenReturn(postDtos);

        List<PostDto> result = postService.getSubscribedPosts(userId);

        assertEquals(postDtos, result);
    }

    @DisplayName("Get subscribed posts - Failure: User not found")
    @Test
    public void testGetSubscribedPosts_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.getSubscribedPosts(1L));
    }
}
