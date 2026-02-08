package com.dobrynya.bookshelf.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileStorageService {
    private final Path uploadPath;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию: " + uploadDir, e);
        }
    }

    public String saveFile(MultipartFile file, Long bookId) {
        String fileName = bookId + ".pdf";
        try {
            Path targetPath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл " + e);
        }
    }

    public byte[] getFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + e);
        }
    }
}
