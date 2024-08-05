package com.openclassrooms.mddapi.security.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service for handling JWT (JSON Web Token) operations.
 * <p>
 * This service provides methods for generating, parsing, and validating JWT
 * tokens.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Service
public class JwtService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    /**
     * Retrieves the signing key used for JWT token creation and validation.
     * 
     * @return the signing key as a {@link SecretKey}
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given user details.
     * 
     * @param userDetails the user details to include in the token
     * @return the generated JWT token as a {@link String}
     */
    public String generateToken(UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); /* 1 day in ms */

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(getSignKey())
                .compact();

        return token;
    }

    /**
     * Extracts claims from the given JWT token.
     * 
     * @param token the JWT token
     * @return the claims contained in the token as a {@link Claims} object
     */
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided
     * resolver function.
     * 
     * @param <T>      the type of the claim
     * @param token    the JWT token
     * @param resolver the function used to extract the claim
     * @return the extracted claim of type {@code T}
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extracts the username from the given JWT token.
     * 
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if the given JWT token is valid for the specified user.
     * 
     * @param token the JWT token
     * @param user  the user details
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean isValid(String token, UserDetailsImpl user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) || username.equals(user.getEmail()) && !isTokenExpired(token));
    }

    /**
     * Checks if the given JWT token has expired.
     * 
     * @param token the JWT token
     * @return {@code true} if the token is expired, {@code false} otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     * 
     * @param token the JWT token
     * @return the expiration date as a {@link Date}
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
