package com.ibrahimyemi.blog_app.post.service;

import com.ibrahimyemi.blog_app.post.dto.PostRequest;
import com.ibrahimyemi.blog_app.post.dto.PostResponse;
import com.ibrahimyemi.blog_app.post.entity.Post;
import com.ibrahimyemi.blog_app.post.repository.PostRepository;
import com.ibrahimyemi.blog_app.security.exceptions.NotFoundException;
import com.ibrahimyemi.blog_app.security.exceptions.UnauthorizedException;
import com.ibrahimyemi.blog_app.user.entity.User;
import com.ibrahimyemi.blog_app.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // Create Post
    public PostResponse createPost(PostRequest request, User user) {

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(user)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post);

        return mapToResponse(post);
    }

    // Get all posts (paginated)
    public Page<PostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // Get posts by author (paginated)
    public Page<PostResponse> getPostsByAuthor(User author, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // Ensure the author exists (optional but recommended)
        Page<Post> posts = postRepository.findAllByAuthor(author, pageable);

        return posts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())));
    }

    // Update Post
    public PostResponse updatePost(UUID postId, PostRequest request, User user) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        // Owner or Admin check
        if (!post.getAuthor().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("You cannot edit this post");
        }

        if (request.getTitle() != null) post.setTitle(request.getTitle());
        if (request.getContent() != null) post.setContent(request.getContent());

        postRepository.save(post);

        return mapToResponse(post);
    }

    // Delete Post
    public void deletePost(UUID postId, User user) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        // Owner or Admin check
        if (!post.getAuthor().getId().equals(user.getId()) && user.getRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("You cannot delete this post");
        }

        postRepository.delete(post);
    }

    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getAuthor().getName())
                .authorEmail(post.getAuthor().getEmail())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
