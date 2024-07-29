package com.openclassrooms.mddapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a topic in the system.
 * This entity is mapped to the 'topics' table in the database.
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "topics")
public class Topic {

    /**
     * Topic's unique ID.
     * This field is the primary key for the topic entity and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    /**
     * Topic's name.
     * This field must be non-null, non-blank, and is required for each topic.
     */
    @NonNull
    @Column(nullable = false)
    @NotBlank
    private String name;

    /**
     * Topic's description.
     * This field must be non-null, non-blank, and provides additional details about the topic.
     */
    @NonNull
    @Column(nullable = false)
    @NotBlank
    private String description;
}
