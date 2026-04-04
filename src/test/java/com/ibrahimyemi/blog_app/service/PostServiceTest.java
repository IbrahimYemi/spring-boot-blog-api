package com.ibrahimyemi.blog_app.service;

import com.ibrahimyemi.blog_app.post.dto.PostRequest;
import com.ibrahimyemi.blog_app.post.entity.Post;
import com.ibrahimyemi.blog_app.post.repository.PostRepository;
import com.ibrahimyemi.blog_app.post.service.PostService;
import com.ibrahimyemi.blog_app.security.exceptions.UnauthorizedException;
import com.ibrahimyemi.blog_app.user.entity.User;
import com.ibrahimyemi.blog_app.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockitoBean
    private PostRepository postRepository;

    @Test
    void shouldThrowWhenNonOwnerTriesToUpdatePost() {
        UUID postId = UUID.randomUUID();

        User owner = User.builder().id(UUID.randomUUID()).role(UserRole.USER).build();
        User stranger = User.builder().id(UUID.randomUUID()).role(UserRole.USER).build();

        Post post = Post.builder().id(postId).author(owner).build();
        PostRequest request = new PostRequest();
        request.setTitle("Updated");

        Mockito.when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        assertThrows(UnauthorizedException.class, () ->
                postService.updatePost(postId, request, stranger)
        );
    }
}
