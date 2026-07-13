package com.dn5.week3.rest.controller;

import com.dn5.week3.rest.dto.LoginRequestDto;
import com.dn5.week3.rest.dto.LoginResponseDto;
import com.dn5.week3.rest.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo authentication endpoint. Issues a JWT for a single hardcoded
 * demo account - NOT suitable for production use.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // DEMO-ONLY hardcoded credential. In a real application, credentials
    // would be verified against a persisted, hashed user store.
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "admin123";

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        if (DEMO_USERNAME.equals(request.getUsername()) && DEMO_PASSWORD.equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new LoginResponseDto(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
