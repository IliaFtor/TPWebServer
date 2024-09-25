package com.example.ametist.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.ametist.Service.UserRepository;
import com.example.ametist.Service.JwtService;
import com.example.ametist.Service.RegisterRequest;
import com.example.ametist.models.User;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtService.generateToken(userDetails);

        return jwt;
    }
    
    public String registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByMail(registerRequest.getEmail())) {
            return "Email already in use";
        }

        User newUser = new User();
        newUser.setName(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setHashPasswoed(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setCreatedTime(LocalDateTime.now());

        userRepository.save(newUser);

        final String jwt = jwtService.generateToken(new org.springframework.security.core.userdetails.User(newUser.getEmail(), newUser.getPasswordHash(), new ArrayList<>()));

        return jwt;
    }
}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


