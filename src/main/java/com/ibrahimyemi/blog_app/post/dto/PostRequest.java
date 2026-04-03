package com.ibrahimyemi.blog_app.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String excerpt;

    private boolean published;

    public boolean getPublished() {
        return published;
    }
}
