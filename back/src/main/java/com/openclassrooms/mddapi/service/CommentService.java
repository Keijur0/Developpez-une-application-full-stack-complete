package com.openclassrooms.mddapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;

/**
 * Service for managing comments in the app.
 * 
 * Provides methods to retrieve and create comments.
 * Interacts with CommentRepository.
 */

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public List<CommentDto> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException());
        List<Comment> postComments = commentRepository.findByPost(post);
        return CommentMapper.INSTANCE.toDto(postComments);
    }

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentRepository.save(CommentMapper.INSTANCE.toEntity(commentDto));
        return CommentMapper.INSTANCE.toDto(comment);
    }
}
