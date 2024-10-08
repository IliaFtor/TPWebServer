package com.example.ametist.Service;

import com.example.ametist.models.Environment;
import com.example.ametist.models.User;
import com.example.ametist.repositories.EnvironmentRepository;
import com.example.ametist.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class My_En_serv {

    private final UserRepository userRepository;
    private final EnvironmentRepository environmentRepository;

    public My_En_serv(UserRepository userRepository, EnvironmentRepository environmentRepository) {
        this.userRepository = userRepository;
        this.environmentRepository = environmentRepository;
    }

    // Method to find user by username
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Method to find user by ID
    public User findUserById(Long authorId) {
        return userRepository.findById(authorId).orElse(null);
    }

    // Method to get environments by author ID
    public List<Environment> getEnvironmentsByAuthorId(Integer authorId) {
        return environmentRepository.findByAuthorId(authorId);
    }
}
