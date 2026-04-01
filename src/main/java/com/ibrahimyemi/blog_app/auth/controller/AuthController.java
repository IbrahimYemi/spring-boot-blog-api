package com.ibrahimyemi.blog_app.auth.controller;

import com.ibrahimyemi.blog_app.common.response.ApiResponse;
import com.ibrahimyemi.blog_app.common.response.AuthResponse;
import com.ibrahimyemi.blog_app.auth.dto.LoginRequest;
import com.ibrahimyemi.blog_app.auth.dto.RegisterRequest;
import com.ibrahimyemi.blog_app.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Application is up and running", "Health check successful")
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(authService.login(request), "Login successfully"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestParam String refreshToken) {
        System.out.println("refresh token: " + refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(authService.refreshToken(refreshToken), "Refreshed token successfully"));
    }
}
