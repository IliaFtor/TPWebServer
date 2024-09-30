package com.example.ametist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ametist.UserRepository;
import com.example.ametist.Utils.JwtUtil;
import com.example.ametist.models.User;
import com.example.ametist.models.Role;

import java.util.ArrayList;
import java.time.*;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(String username, String password, String email) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User newUser = new User();
        newUser.setName(username);
        newUser.setEmail(email);
        newUser.setHashPassword(passwordEncoder.encode(password)); 
        newUser.setCreatedTime(LocalDateTime.now());
        userRepository.save(newUser);

        String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
            newUser.getName(), newUser.getHashPassword(), new ArrayList<>()
        ));

        return token;
    }
}
