package com.ibrahimyemi.blog_app.common.service.filestorage.strategy;

import com.ibrahimyemi.blog_app.common.service.filestorage.FileStorageStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryFileStorageStrategy implements FileStorageStrategy {

    @Override
    public String store(MultipartFile file) {
        // TODO: upload to Cloudinary
        return "cloudinary-url";
    }

    @Override
    public void delete(String filePath) {

    }

    @Override
    public String getType() {
        return "Cloudinary";
    }
}
