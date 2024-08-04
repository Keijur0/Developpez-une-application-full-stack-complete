package com.openclassrooms.mddapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageReponse;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.service.JwtService;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.security.service.UserDetailsServiceImpl;

@DisplayName("Authentication service unit tests")
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @DisplayName("Login - Success")
    @Test
    public void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest(
                "testuser",
                "Test!1234");
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                "test",
                "test@test.com",
                "Test!1234");
        String token = "jwt-token";

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(token);

        ResponseEntity<?> response = authService.login(loginRequest);

        AuthResponse authResponse = new AuthResponse(token, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail());
        assertEquals(ResponseEntity.ok(authResponse), response);
    }

    @DisplayName("Register - Success")
    @Test
    public void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest(
                "test",
                "test@test.com",
                "Test!1234");
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword("encoded-password");

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encoded-password");

        ResponseEntity<?> response = authService.register(registerRequest);

        MessageReponse msgResponse = new MessageReponse("User successfully registered");
        assertEquals(ResponseEntity.ok(msgResponse), response);
        verify(userRepository, times(1)).save(user);
    }

    @DisplayName("Register - Failure: Email already in use")
    @Test
    public void testRegister_EmailAlreadyInUse() {
        RegisterRequest registerRequest = new RegisterRequest(
                "newuser",
                "existing@example.com",
                "Test!1234");

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        ResponseEntity<?> response = authService.register(registerRequest);

        MessageReponse msgResponse = new MessageReponse("Email already in use");
        assertEquals(ResponseEntity.badRequest().body(msgResponse), response);
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Register - Failure: Username already in use")
    @Test
    public void testRegister_UsernameAlreadyInUse() {
        RegisterRequest registerRequest = new RegisterRequest(
                "test",
                "test@test.com",
                "Test!1234");

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(true);

        ResponseEntity<?> response = authService.register(registerRequest);

        MessageReponse msgResponse = new MessageReponse("Username already in use");
        assertEquals(ResponseEntity.badRequest().body(msgResponse), response);
        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("Me - Success")
    @Test
    public void testMe_Success() {
        String username = "testuser";
        UserDetailsImpl userDetails = new UserDetailsImpl(
                1L,
                "testuser",
                "testuser@test.com",
                "encoded-password");

        when(authentication.getName()).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        ResponseEntity<?> response = authService.me();

        AuthResponse expectedResponse = new AuthResponse(
                "",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail());

        assertEquals(ResponseEntity.ok(expectedResponse), response);
    }
}
