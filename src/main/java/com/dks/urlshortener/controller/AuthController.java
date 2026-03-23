package com.dks.urlshortener.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dks.urlshortener.model.User;
import com.dks.urlshortener.repository.UserRepository;
import com.dks.urlshortener.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    
	private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        userRepository.save(user);
        return "User registered successfully";
    }
    
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existing = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        if (!existing.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        System.out.println("JWT UTIL: " + jwtUtil);
        System.out.println(jwtUtil.generateToken(existing.getEmail()));
        return jwtUtil.generateToken(existing.getEmail());
    }
}