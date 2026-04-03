package com.ibrahimyemi.blog_app.common.service.filestorage.strategy;

import com.ibrahimyemi.blog_app.common.service.filestorage.FileStorageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3FileStorageStrategy implements FileStorageStrategy {

    @Override
    public String store(MultipartFile file) {
        // TODO: upload to AWS S3
        return "s3-file-url";
    }

    @Override
    public void delete(String filePath) {

    }

    @Override
    public String getType() {
        return "s3";
    }
}