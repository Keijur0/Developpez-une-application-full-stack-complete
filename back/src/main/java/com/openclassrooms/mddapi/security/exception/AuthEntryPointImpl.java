package com.openclassrooms.mddapi.security.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AuthenticationEntryPoint} that rejects unauthorized
 * requests.
 * <p>
 * This class sends an HTTP 401 Unauthorized error in response to unauthorized
 * requests.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Component
public class AuthEntryPointImpl implements AuthenticationEntryPoint {

    /**
     * Responds with an HTTP 401 Unauthorized error.
     *
     * @param request       the HTTP request
     * @param response      the HTTP response
     * @param authException the authentication exception
     * @throws IOException      if an input or output error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}
