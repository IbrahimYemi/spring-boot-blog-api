package com.ibrahimyemi.blog_app.auth.service;

import com.ibrahimyemi.blog_app.common.response.AuthResponse;
import com.ibrahimyemi.blog_app.auth.dto.LoginRequest;
import com.ibrahimyemi.blog_app.auth.dto.RegisterRequest;
import com.ibrahimyemi.blog_app.security.exceptions.BadRequestException;
import com.ibrahimyemi.blog_app.security.exceptions.NotFoundException;
import com.ibrahimyemi.blog_app.security.exceptions.UnauthorizedException;
import com.ibrahimyemi.blog_app.security.jwt.JwtService;
import com.ibrahimyemi.blog_app.user.entity.User;
import com.ibrahimyemi.blog_app.user.enums.UserRole;
import com.ibrahimyemi.blog_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Registers a new user.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            log.warn("Attempted registration with existing email: {}", request.getEmail());
            throw new BadRequestException("Email already in use");
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return buildAuthResponse(user);
    }

    /**
     * Logs in an existing user.
     */
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return buildAuthResponse(user);
    }

    /**
     * Refreshes access token using a valid refresh token.
     */
    public AuthResponse refreshToken(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        String email;
        try {
            email = jwtService.extractEmail(refreshToken);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!jwtService.isRefreshTokenValid(refreshToken, user.getEmail())) {
            throw new UnauthorizedException("Invalid or expired refresh token");
        }

        // Optionally rotate refresh token for extra security
        String newAccessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken) // Consider rotating in prod
                .build();
    }

    /**
     * Builds JWT tokens for a user.
     */
    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
