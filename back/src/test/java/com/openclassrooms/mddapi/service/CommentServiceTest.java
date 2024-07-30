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

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

@DisplayName("Comment service unit tests")
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentMapper commentMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Get comments - Success")
    @Test
    public void testGetComments_Success() {
        Long postId = 1L;
        Post post = new Post();
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        List<Comment> comments = List.of(comment);
        List<CommentDto> commentDtos = List.of(commentDto);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findByPost(post)).thenReturn(comments);
        when(commentMapper.toDto(comments)).thenReturn(commentDtos);

        List<CommentDto> result = commentService.getComments(postId);

        assertEquals(commentDtos, result);
    }

    @DisplayName("Get comments - Failure: Post not found")
    @Test
    public void testGetComments_PostNotFound() {
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> commentService.getComments(postId));
    }

    @DisplayName("Create comment - Success")
    @Test
    public void testCreateComment_Success() {
        CommentDto commentDto = new CommentDto();
        Comment comment = new Comment();
        CommentDto createdCommentDto = new CommentDto();

        when(commentMapper.toEntity(commentDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(createdCommentDto);

        CommentDto result = commentService.createComment(commentDto);

        assertEquals(createdCommentDto, result);
    }
}
