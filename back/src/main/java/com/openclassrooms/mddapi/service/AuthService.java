package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
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
 * Implements the {@link IAuthService} interface to provide login and registration functionalities.
 */
@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Authenticates a user based on the provided login request.
     *
     * @param loginRequest the login request containing the user's username/email and password
     * @return a {@link ResponseEntity} containing a success or error message
     */
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(), 
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param registerRequest the registration request containing the user's details
     * @return a {@link ResponseEntity} containing a success or error message
     */
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) {

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Email already in use"));
        }

        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageReponse("Username already in use"));
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageReponse("User successfully registered"));
    }
}
