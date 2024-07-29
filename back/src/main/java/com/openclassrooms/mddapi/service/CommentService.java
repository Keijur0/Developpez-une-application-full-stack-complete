package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

/**
 * Service for managing comments in the application.
 * 
 * This service provides methods to retrieve and create comments associated with posts.
 * It interacts with the {@link CommentRepository} and {@link PostRepository} for data access operations.
 * 
 * <p>Uses {@link CommentMapper} for converting between entity and DTO representations of comments.</p>
 */
@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
            CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Retrieves all comments associated with a specific post.
     * 
     * @param postId the unique identifier of the post
     * @return a list of {@link CommentDto} objects representing the comments of the specified post
     * @throws NotFoundException if the post with the specified ID is not found
     */
    public List<CommentDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException());
        List<Comment> postComments = commentRepository.findByPost(post);
        return commentMapper.toDto(postComments);
    }

    /**
     * Creates a new comment.
     * 
     * @param commentDto the data transfer object containing the details of the comment to be created
     * @return the created {@link CommentDto}
     */
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentRepository.save(commentMapper.toEntity(commentDto));
        return commentMapper.toDto(comment);
    }
}
