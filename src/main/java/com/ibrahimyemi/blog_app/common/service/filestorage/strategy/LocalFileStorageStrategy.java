package com.ibrahimyemi.blog_app.common.service.filestorage.strategy;


import com.ibrahimyemi.blog_app.common.service.filestorage.FileStorageStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageStrategy implements FileStorageStrategy {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String store(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new RuntimeException("Invalid file name");
            }

            String extension = getExtension(originalFilename);
            String filename = UUID.randomUUID() + "." + extension;

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/" + filePath;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void delete(String filePath) {
        try {
            if (filePath == null || filePath.isBlank()) return;

            // extract filename from URL
            String filename = Paths.get(filePath).getFileName().toString();

            Path fullPath = Paths.get(uploadDir).resolve(filename);

            Files.deleteIfExists(fullPath);

        } catch (IOException e) {
            // fail silently
            System.err.println("Failed to delete file: " + filePath);
        }
    }


    @Override
    public String getType() {
        return "local";
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}