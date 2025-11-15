package com.example.Todo.services;

import org.springframework.stereotype.Service;

import com.example.Todo.entities.User;
import com.example.Todo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ✅ Dummy user for local testing
    private final User dummyUser = User.builder()
            .email("test@example.com")
            .name("Dev User")
            .build();

    /**
     * ✅ Always return the dummy user (create if not exists)
     */
    public User getCurrentUser() {
        return userRepository.findByEmail(dummyUser.getEmail())
                .orElseGet(() -> userRepository.save(dummyUser));
    }
}
