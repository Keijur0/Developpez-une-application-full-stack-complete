package com.openclassrooms.mddapi.security.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Implementation of {@link UserDetails} interface for Spring Security.
 * <p>
 * This class represents user details used by Spring Security for authentication
 * and authorization.
 * It implements the {@link UserDetails} interface to provide user information
 * and account status.
 * </p>
 * 
 * @version 1.0
 * @since 2024-07-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    /**
     * The unique identifier for the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * Returns the authorities granted to the user.
     * <p>
     * This method currently returns an empty set as authorities are not
     * implemented.
     * </p>
     * 
     * @return a collection of {@link GrantedAuthority} instances representing the
     *         authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

    /**
     * Indicates whether the user's account has expired.
     * <p>
     * This method currently always returns {@code true}, indicating that the
     * account is not expired.
     * </p>
     * 
     * @return {@code true} if the user's account is not expired; {@code false}
     *         otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     * <p>
     * This method currently always returns {@code true}, indicating that the
     * account is not locked.
     * </p>
     * 
     * @return {@code true} if the user's account is not locked; {@code false}
     *         otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * <p>
     * This method currently always returns {@code true}, indicating that the
     * credentials are not expired.
     * </p>
     * 
     * @return {@code true} if the user's credentials are not expired; {@code false}
     *         otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled and able to authenticate.
     * <p>
     * This method currently always returns {@code true}, indicating that the user
     * is enabled.
     * </p>
     * 
     * @return {@code true} if the user is enabled; {@code false} otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
