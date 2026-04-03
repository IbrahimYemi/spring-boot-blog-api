package com.ibrahimyemi.blog_app.service;

import com.ibrahimyemi.blog_app.post.dto.PostRequest;
import com.ibrahimyemi.blog_app.post.entity.Post;
import com.ibrahimyemi.blog_app.post.service.PostService;
import com.ibrahimyemi.blog_app.security.exceptions.UnauthorizedException;
import com.ibrahimyemi.blog_app.user.entity.User;
import com.ibrahimyemi.blog_app.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void shouldThrowWhenNonOwnerTriesToUpdatePost() {

        User owner = User.builder()
                .id(UUID.randomUUID())
                .role(UserRole.USER)
                .build();

        User stranger = User.builder()
                .id(UUID.randomUUID())
                .role(UserRole.USER)
                .build();

        Post post = Post.builder()
                .id(UUID.randomUUID())
                .author(owner)
                .build();

        PostRequest request = new PostRequest();
        request.setTitle("Updated");

        assertThrows(UnauthorizedException.class, () ->
                postService.updatePost(post.getId(), request, stranger)
        );
    }
}
