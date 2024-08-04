package com.openclassrooms.mddapi.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Implementation of {@link UserDetailsService} for loading user-specific data.
 * <p>
 * This service is used by Spring Security to retrieve user details based on the
 * provided username or email.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a {@link UserDetailsServiceImpl} with the specified
     * {@link UserRepository}.
     * 
     * @param userRepository the repository used to access user data
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user-specific data by username or email.
     * <p>
     * This method retrieves the user from the repository based on the provided
     * username or email.
     * If the user is found, it returns an instance of {@link UserDetailsImpl}.
     * If the user is not found, it throws a {@link UsernameNotFoundException}.
     * </p>
     * 
     * @param usernameOrEmail the username or email of the user to be retrieved
     * @return a {@link UserDetails} object containing user details
     * @throws UsernameNotFoundException if no user is found with the provided
     *                                   username or email
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username/email: " + usernameOrEmail));
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

}
