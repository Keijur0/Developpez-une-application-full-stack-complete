package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Topic;

/**
 * Repository interface for {@link Topic} entities.
 * <p>
 * This interface provides methods for performing CRUD operations on topics.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

}
