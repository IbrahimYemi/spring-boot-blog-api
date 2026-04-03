package com.ibrahimyemi.blog_app.service;

import com.ibrahimyemi.blog_app.auth.dto.ChangePasswordRequest;
import com.ibrahimyemi.blog_app.security.exceptions.UnauthorizedException;
import com.ibrahimyemi.blog_app.user.entity.User;
import com.ibrahimyemi.blog_app.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldFailWhenCurrentPasswordIsWrong() {

        User user = User.builder()
                .password(passwordEncoder.encode("correctPassword"))
                .build();

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("wrongPassword");
        request.setNewPassword("newPassword123");

        assertThrows(UnauthorizedException.class, () ->
                userService.changePassword(request, user)
        );
    }
}
