package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a user in the system.
 * This entity is mapped to the 'users' table in the database.
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    /**
     * User's unique ID.
     * This field is the primary key for the user entity and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * User's unique username.
     * This field must be non-null, non-blank, and unique across all users.
     */
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String username;

    /**
     * User's unique email address.
     * This field must be non-null, non-blank, and a valid email format.
     * It must also be unique across all users.
     */
    @NonNull
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    /**
     * User's password.
     * This field must be non-null and non-blank.
     */
    @NonNull
    @NotBlank
    private String password;

    /**
     * User's topics subscriptions.
     * Represents a many-to-many relationship between users and topics.
     */
    @ManyToMany
    @JoinTable(
            name = "Subscriptions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private List<Topic> subscriptions;

    /**
     * User's creation date.
     * Automatically set to the timestamp when the user is created.
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * User's account last update date.
     * Automatically updated to the timestamp when the user entity is updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
