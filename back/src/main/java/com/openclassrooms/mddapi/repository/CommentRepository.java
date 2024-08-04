package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;

/**
 * Repository interface for managing {@link Comment} entities.
 * 
 * Provides methods for retrieving comments from the database.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves a list of comments associated with a specific post.
     *
     * @param post the post for which to retrieve comments
     * @return a list of comments associated with the given post
     */
    List<Comment> findByPost(Post post);
}
