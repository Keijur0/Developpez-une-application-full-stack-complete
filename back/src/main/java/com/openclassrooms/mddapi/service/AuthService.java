package com.openclassrooms.mddapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageReponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.service.JwtService;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.security.service.UserDetailsServiceImpl;

/**
 * Service class for authentication operations.
 * <p>
 * This class implements the {@link IAuthService} interface to provide
 * functionalities
 * such as user login and registration. It handles user authentication, token
 * generation,
 * and user registration, and interacts with the {@link UserRepository} to
 * manage user data.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructs an {@link AuthService} with the specified {@link UserRepository},
     * {@link PasswordEncoder}, {@link JwtService}, {@link AuthenticationManager},
     * and {@link UserDetailsServiceImpl}.
     * 
     * @param userRepository     the repository used to access user data
     * @param passwordEncoder    the encoder used to hash passwords
     * @param jwtService         the service used to generate and validate JWT
     *                           tokens
     * @param authManager        the manager used to handle authentication
     * @param userDetailsService the service used to load user details
     */
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authManager, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Authenticates a user based on the provided login request.
     * <p>
     * This method validates the user's credentials and, if successful, generates a
     * JWT token
     * and returns it along with user details.
     * </p>
     * 
     * @param loginRequest the login request containing the user's username/email
     *                     and password
     * @return a {@link ResponseEntity} containing an {@link AuthResponse} with the
     *         JWT token
     *         and user details if authentication is successful
     */
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(loginRequest.getUsernameOrEmail());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(
                token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    /**
     * Registers a new user based on the provided registration request.
     * <p>
     * This method checks if the email or username already exists and, if not,
     * creates a new user,
     * hashes their password, and saves the user to the repository.
     * </p>
     * 
     * @param registerRequest the registration request containing the user's details
     * @return a {@link ResponseEntity} containing a {@link MessageReponse} with a
     *         success
     *         or error message
     */
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Email already in use"));
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Username already in use"));
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageReponse("User successfully registered"));
    }

    /**
     * Retrieves the details of the currently authenticated user.
     * <p>
     * This method extracts the username from the security context, loads the user
     * details,
     * and returns them in an {@link AuthResponse}.
     * </p>
     * 
     * @return a {@link ResponseEntity} containing an {@link AuthResponse} with the
     *         user details
     */
    @Override
    public ResponseEntity<?> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(new AuthResponse(
                "",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }
}
