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

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentMapper.userRepository = userRepository;
        commentMapper.postRepository = postRepository;
    }

    @DisplayName("To comment dto - Success")
    @Test
    public void testToDto_Success() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setMessage("Test Content");

        User user = new User();
        user.setId(1L);
        comment.setUser(user);

        Post post = new Post();
        post.setId(1L);
        comment.setPost(post);

        CommentDto commentDto = commentMapper.toDto(comment);

        assertNotNull(commentDto);
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getMessage(), commentDto.getMessage());
        assertEquals(comment.getUser().getId(), commentDto.getUserId());
        assertEquals(comment.getPost().getId(), commentDto.getPostId());
    }

    @DisplayName("To comment entity - Success")
    @Test
    public void testToEntity_Success() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setMessage("Test Content");
        commentDto.setUserId(1L);
        commentDto.setPostId(1L);

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Post post = new Post();
        post.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Comment comment = commentMapper.toEntity(commentDto);

        assertNotNull(comment);
        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getMessage(), comment.getMessage());
        assertEquals(commentDto.getUserId(), comment.getUser().getId());
        assertEquals(commentDto.getPostId(), comment.getPost().getId());
    }

    @DisplayName("To comment entity - Failure: User not found")
    @Test
    public void testToEntity_UserNotFound() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentMapper.toEntity(commentDto));
    }

    @DisplayName("To comment entity - Failure: Post not found")
    @Test
    public void testToEntity_PostNotFound() {
        CommentDto commentDto = new CommentDto();
        commentDto.setPostId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentMapper.toEntity(commentDto));
    }

    @DisplayName("To comment dtos - Success")
    @Test
    public void testToDtos_Success() {
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setMessage("Content 1");

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setMessage("Content 2");

        List<Comment> comments = List.of(comment1, comment2);
        List<CommentDto> commentDtos = commentMapper.toDtos(comments);

        assertNotNull(commentDtos);
        assertEquals(2, commentDtos.size());
        assertEquals(comment1.getId(), commentDtos.get(0).getId());
        assertEquals(comment2.getId(), commentDtos.get(1).getId());
    }
}
