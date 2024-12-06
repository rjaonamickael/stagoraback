package com.stagora.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageStorageService {

    @Value("${image.upload-dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path filePath = path.resolve(file.getOriginalFilename());
        file.transferTo(filePath.toFile());

        return "Image enregistrée avec succès : " + file.getOriginalFilename();
    }
}
