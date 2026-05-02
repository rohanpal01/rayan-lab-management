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
        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("❌ User not found. Please register first.");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("❌ Invalid password");
        }

        return ResponseEntity.ok(
                Map.of("message", "Login successful", "role", user.getRole())
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");
        String role = body.get("role"); // 👈 NEW

        // ✅ Validate role
        if (role == null || role.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("❌ Role is required");
        }

        // ✅ Normalize role
        role = role.toUpperCase();

        // ✅ Allow only valid roles
        if (!role.equals("ADMIN") &&
                !role.equals("TECHNICIAN") &&
                !role.equals("RECEPTION")) {

            return ResponseEntity
                    .badRequest()
                    .body("❌ Invalid role selected");
        }

        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("❌ Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // ✅ Append ROLE_
        user.setRole("ROLE_" + role);

        userRepository.save(user);

        return ResponseEntity.ok("✅ User registered successfully");
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

