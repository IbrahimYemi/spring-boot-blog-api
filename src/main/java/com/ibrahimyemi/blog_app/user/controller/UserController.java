package com.ibrahimyemi.blog_app.user.controller;

import com.ibrahimyemi.blog_app.auth.dto.ChangePasswordRequest;
import com.ibrahimyemi.blog_app.auth.entity.AppUserDetails;
import com.ibrahimyemi.blog_app.common.response.ApiResponse;
import com.ibrahimyemi.blog_app.security.exceptions.BadRequestException;
import com.ibrahimyemi.blog_app.user.dto.UpdateProfileRequest;
import com.ibrahimyemi.blog_app.user.dto.UserProfileResponse;
import com.ibrahimyemi.blog_app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            @AuthenticationPrincipal AppUserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        userService.getProfile(userDetails.getUser()),
                        "User profile successfully retrieved"));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        userService.updateProfile(request, userDetails.getUser()),
                        "User profile successfully updated"));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        userService.changePassword(request, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(null, "Password updated successfully"));
    }

    @PutMapping("/me/avatar")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateAvatar(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new BadRequestException("Only image files allowed");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        userService.updateAvatar(file, userDetails.getUser()),
                        "User avatar successfully updated"));
    }
}
