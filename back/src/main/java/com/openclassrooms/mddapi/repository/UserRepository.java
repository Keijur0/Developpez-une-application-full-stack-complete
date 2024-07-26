package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.User;

/**
 * Repository interface for {@link User} entities.
 * Provides methods for performing CRUD operations and additional query methods
 * related to user entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username or email.
     *
     * @param username the username of the user
     * @param email the email of the user
     * @return an {@link Optional} containing the found user, or {@link Optional#empty()} if no user found
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Checks if a user exists with the given email.
     *
     * @param email the email to check
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    Boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     *
     * @param username the username to check
     * @return {@code true} if a user with the given username exists, {@code false} otherwise
     */
    Boolean existsByUsername(String username);
}
