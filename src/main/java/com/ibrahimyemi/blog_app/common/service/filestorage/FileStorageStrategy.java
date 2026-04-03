package com.ibrahimyemi.blog_app.common.service.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageStrategy {
    String store(MultipartFile file);
    void delete(String filePath);
    String getType(); // channel name: local, s3, etc.
}
