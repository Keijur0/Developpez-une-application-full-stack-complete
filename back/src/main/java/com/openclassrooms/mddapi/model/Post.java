package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a post in the system.
 * This entity is mapped to the 'posts' table in the database.
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    /**
     * Post's unique ID.
     * This field is the primary key for the post entity and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    /**
     * Topic associated with the post.
     * This field represents a many-to-one relationship with the {@link Topic} entity.
     */
    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "topic_id")
    private Topic topic;

    /**
     * Post's title.
     * This field must be non-null and non-blank.
     */
    @NotNull
    @NotBlank
    private String title;

    /**
     * Author of the post.
     * This field represents a many-to-one relationship with the {@link User} entity.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id")
    private User user;

    /**
     * Post's content.
     * This field must be non-null and non-blank.
     */
    @NotNull
    @NotBlank
    private String content;

    /**
     * Post's creation date.
     * This field is automatically set to the date when the post is created.
     */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
