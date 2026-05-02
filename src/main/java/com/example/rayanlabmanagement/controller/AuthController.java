package com.example.rayanlabmanagement.controller;

import com.example.rayanlabmanagement.entity.User;
import com.example.rayanlabmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepository userRepository;




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok(Map.of("message", "Login successful", "role", user.getRole()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            // Auto-register if user doesn’t exist
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password); // plain text
            newUser.setRole("ROLE_USER");
            userRepository.save(newUser);
            return ResponseEntity.ok(Map.of("message", "User registered and logged in", "role", newUser.getRole()));
        }
    }

    @GetMapping("/admin-only")
    public ResponseEntity<String> adminEndpoint(@RequestParam String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        if (!user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return ResponseEntity.ok("Welcome Admin!");
    }



   /* @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setPassword(passwordEncoder.encode(body.get("password"))); // store hash
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }*/
}

