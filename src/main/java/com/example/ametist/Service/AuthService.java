package com.example.ametist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ametist.Utils.JwtUtil;
import com.example.ametist.models.User;
import com.example.ametist.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, String> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getHashPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userOptional.get().getName(),
                userOptional.get().getHashPassword(),
                new ArrayList<>()
        );

        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }
}
