package com.ibrahimyemi.blog_app.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserProfileResponse {
    private UUID    id;
    private String  name;
    private String  email;
    private String  avatarUrl;
    private String  role;
}
