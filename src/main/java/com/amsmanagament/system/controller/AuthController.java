package com.amsmanagament.system.controller;

import com.amsmanagament.system.config.JwtProvider;
import com.amsmanagament.system.model.User;
import com.amsmanagament.system.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Signup
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) {

        if (userRepo.findByPhoneNumber(user.getPhoneNumber()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse("Phone number already in use",null, null));
        }

        // 👉 store raw password BEFORE encoding
        String rawPassword = user.getPassword();

        // encode password for database
        user.setPassword(passwordEncoder.encode(rawPassword));
        User savedUser = userRepo.save(user);

        // 👉 authenticate using raw password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(savedUser.getPhoneNumber(), rawPassword)
        );

        String jwt = jwtProvider.generateToken(authentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Registration successful", jwt, savedUser.getRole()));
    }

    // Signin
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getPhoneNumber(), req.getPassword())
        );

        String jwt = jwtProvider.generateToken(authentication);
        User user = userRepo.findByPhoneNumber(req.getPhoneNumber());

        return ResponseEntity.ok(new AuthResponse("Login successful", jwt, user.getRole()));
    }
}
