package com.openclassrooms.mddapi.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username/email: " + usernameOrEmail));
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
    }

}
