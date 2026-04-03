package com.ibrahimyemi.blog_app.post.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PostResponse {
    private UUID id;
    private String title;
    private String content;
    private String authorName;
    private String authorEmail;
    private boolean published;
    private LocalDateTime createdAt;
}
