package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;

/**
 * Repository interface for {@link Post} entities.
 * <p>
 * This interface provides methods for performing CRUD operations on posts
 * and retrieving posts associated with specific topics.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Retrieves a list of posts associated with the given list of topics.
     * 
     * @param topics the list of topics to filter posts by
     * @return a list of posts associated with the specified topics
     */
    List<Post> findByTopicIn(List<Topic> topics);
}
