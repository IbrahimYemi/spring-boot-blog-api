package com.ibrahimyemi.blog_app.common.service.filestorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileStorageService {

    private final Map<String, FileStorageStrategy> strategyMap;

    @Value("${file.upload-channel}")
    private String uploadChannel;

    public FileStorageService(List<FileStorageStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(FileStorageStrategy::getType, s -> s));
    }

    public String saveFile(MultipartFile file) {
        return getStrategy().store(file);
    }

    public void deleteFile(String oldFilePath) {
        FileStorageStrategy strategy = getStrategy();
        strategy.delete(oldFilePath);
    }

    public String replaceFile(MultipartFile file, String oldFilePath) {

        String avatarUrl = saveFile(file);
        deleteFile(oldFilePath);

        return avatarUrl;
    }

    private FileStorageStrategy getStrategy() {
        FileStorageStrategy strategy = strategyMap.get(uploadChannel);

        if (strategy == null) {
            throw new RuntimeException("Unsupported upload channel: " + uploadChannel);
        }

        return strategy;
    }
}