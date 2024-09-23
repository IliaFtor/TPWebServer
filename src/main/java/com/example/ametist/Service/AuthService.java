package com.example.ametist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ametist.UserRepository;
import com.example.ametist.Utils.JwtUtil;
import com.example.ametist.models.User;

import java.util.ArrayList;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists!");
        }

        User user = new User();
        user.setName(username);
        user.setHashPasswoed(passwordEncoder.encode(password));
        user.setRole(com.example.ametist.models.Role.Roles.member);

        userRepository.save(user);

        return jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(user.getName(), user.getEmail(), new ArrayList<>()));
    }
}
