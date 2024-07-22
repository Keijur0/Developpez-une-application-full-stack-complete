package com.openclassrooms.mddapi.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "username", "email" })
})
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
    private String username;

    /**
     * User's unique email address.
     */
    @NonNull
    private String email;

    /**
     * User's password.
     */
    @NonNull
    private String password;

    /**
     * User's topic subscriptions
     */
    private List<Topic> subcriptions;
}
