package com.ibrahimyemi.blog_app.user.service;

import com.ibrahimyemi.blog_app.auth.dto.ChangePasswordRequest;
import com.ibrahimyemi.blog_app.common.service.filestorage.FileStorageService;
import com.ibrahimyemi.blog_app.security.exceptions.BadRequestException;
import com.ibrahimyemi.blog_app.security.exceptions.UnauthorizedException;
import com.ibrahimyemi.blog_app.user.dto.UpdateProfileRequest;
import com.ibrahimyemi.blog_app.user.dto.UserProfileResponse;
import com.ibrahimyemi.blog_app.user.entity.User;
import com.ibrahimyemi.blog_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Value("${server.base-url}")
    private String appDomain;

    public String buildAppUrl(String path) {
        System.out.println(appDomain);
        return appDomain + path;
    }

    public UserProfileResponse getProfile(User user) {
        return mapToResponse(user);
    }

    public UserProfileResponse updateProfile(UpdateProfileRequest request, User user) {

        // Check if email is already taken by another user
        userRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(user.getId()))
                .ifPresent(u -> {
                    throw new BadRequestException("Email already in use");
                });

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return mapToResponse(user);
    }

    public void changePassword(ChangePasswordRequest request, User user) {

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserProfileResponse updateAvatar(MultipartFile file, User user) {

        String filePath = fileStorageService.replaceFile(file, user.getAvatarUrl());

        user.setAvatarUrl(filePath);
        userRepository.save(user);

        return mapToResponse(user);
    }

    private UserProfileResponse mapToResponse(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .avatarUrl(buildAppUrl(user.getAvatarUrl()))
                .role(user.getRole().name())
                .build();
    }
}
