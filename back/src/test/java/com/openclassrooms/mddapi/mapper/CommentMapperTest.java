package com.openclassrooms.mddapi.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@DisplayName("Comment mapper unit tests")
public class CommentMapperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentMapperImpl commentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("To comment dto - Success")
    @Test
    public void testToDto_Success() {
        User user = new User();
        user.setId(1L);
        Post post = new Post();
        post.setId(2L);
        
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setPost(post);
        comment.setMessage("Test Comment");
        comment.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = commentMapper.toDto(comment);

        assertNotNull(commentDto);
        assertEquals(1L, commentDto.getUserId());
        assertEquals(2L, commentDto.getPostId());
        assertEquals("Test Comment", commentDto.getMessage());
    }

    @DisplayName("To comment entity - Success")
    @Test
    public void testToEntity_Success() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1L);
        commentDto.setPostId(2L);
        commentDto.setMessage("Test Comment");

        User user = new User();
        user.setId(1L);
        Post post = new Post();
        post.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(2L)).thenReturn(Optional.of(post));

        Comment comment = commentMapper.toEntity(commentDto);

        assertNotNull(comment);
        assertEquals(user, comment.getUser());
        assertEquals(post, comment.getPost());
        assertEquals("Test Comment", comment.getMessage());
    }

    @DisplayName("To comment entity - Failure: User not found")
    @Test
    public void testToEntity_UserNotFound() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1L);
        commentDto.setPostId(2L);
        commentDto.setMessage("Test Comment");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(postRepository.findById(2L)).thenReturn(Optional.of(new Post()));

        Comment comment = commentMapper.toEntity(commentDto);

        assertNotNull(comment);
        assertNull(comment.getUser());
        assertNotNull(comment.getPost());
        assertEquals("Test Comment", comment.getMessage());
    }

    @DisplayName("To comment entity - Failure: Post not found")
    @Test
    public void testToEntity_PostNotFound() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1L);
        commentDto.setPostId(2L);
        commentDto.setMessage("Test Comment");

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(postRepository.findById(2L)).thenReturn(Optional.empty());

        Comment comment = commentMapper.toEntity(commentDto);

        assertNotNull(comment);
        assertNotNull(comment.getUser());
        assertNull(comment.getPost());
        assertEquals("Test Comment", comment.getMessage());
    }

    @DisplayName("To comment dto list - Success")
    @Test
    public void testToDtos_Success() {
        User user = new User();
        user.setId(1L);
        Post post = new Post();
        post.setId(2L);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setPost(post);
        comment.setMessage("Test Comment");

        List<Comment> commentList = Collections.singletonList(comment);

        List<CommentDto> commentDtoList = commentMapper.toDtos(commentList);

        assertNotNull(commentDtoList);
        assertEquals(1, commentDtoList.size());
        CommentDto commentDto = commentDtoList.get(0);
        assertEquals(1L, commentDto.getUserId());
        assertEquals(2L, commentDto.getPostId());
        assertEquals("Test Comment", commentDto.getMessage());
    }
}
