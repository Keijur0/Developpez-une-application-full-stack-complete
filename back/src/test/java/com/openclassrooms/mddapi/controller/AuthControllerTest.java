package com.openclassrooms.mddapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.service.IAuthService;

@DisplayName("Authentication controller unit tests")
public class AuthControllerTest {

    @Mock
    private IAuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Login test - Success")
    @Test
    public void testLogin_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest(
                            "test",
                            "password");

        when(authService.login(loginRequest)).thenReturn(ResponseEntity.ok(null));

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @DisplayName("Login test - Failure")
    @Test
    public void testLogin_Failure() throws Exception {
        LoginRequest loginRequest = new LoginRequest(
                            "test",
                            "wrongpassword");

        when(authService.login(loginRequest)).thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Register test - Success")
    @Test
    public void testRegister_Success() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                            "newuser",
                            "newuser@test.com",
                            "password");

        when(authService.register(registerRequest)).thenReturn(ResponseEntity.ok(null));

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());
    }

    @DisplayName("Register test - Failure")
    @Test
    public void testRegister_Failure() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                            "",
                            "newuser@test.com",
                            "password");

        when(authService.register(registerRequest)).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }
}
