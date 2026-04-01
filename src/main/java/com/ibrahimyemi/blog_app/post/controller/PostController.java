package com.ibrahimyemi.blog_app.post.controller;

import com.ibrahimyemi.blog_app.auth.entity.AppUserDetails;
import com.ibrahimyemi.blog_app.common.response.ApiResponse;
import com.ibrahimyemi.blog_app.post.dto.PostRequest;
import com.ibrahimyemi.blog_app.post.dto.PostResponse;
import com.ibrahimyemi.blog_app.post.service.PostService;
import com.ibrahimyemi.blog_app.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        User user = userDetails.getUser();
        PostResponse post = postService.createPost(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(post, "Post created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<PostResponse> posts = postService.getPosts(page, size);

        return ResponseEntity.ok(
                ApiResponse.success(posts, "Posts retrieved successfully")
        );
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getPostsByAuthor(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Page<PostResponse> posts = postService.getPostsByAuthor(userDetails.getUser(), page, size);

        return ResponseEntity.ok(
                ApiResponse.success(posts, "Author posts retrieved successfully")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody PostRequest request,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        User user = userDetails.getUser();
        PostResponse post = postService.updatePost(id, request, user);

        return ResponseEntity.ok(
                ApiResponse.success(post, "Post updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletePost(
            @PathVariable UUID id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        User user = userDetails.getUser();
        postService.deletePost(id, user);

        return ResponseEntity.ok(
                ApiResponse.success(null, "Post deleted successfully")
        );
    }
}
