package com.openclassrooms.mddapi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NonNull;

/**
 * Represents a user in the system.
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * User's unique ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * User's unique username.
     */
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String username;

    /**
     * User's unique email address.
     */
    @NonNull
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    /**
     * User's password.
     */
    @NonNull
    @NotBlank
    private String password;

    /**
     * User's topic subscriptions
     */
    private List<Topic> subscriptions;

    /**
     * User's creation date
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * User's account last update date
     */
    @Column(name = "updated_at")
    private Date updatedAt;
}
