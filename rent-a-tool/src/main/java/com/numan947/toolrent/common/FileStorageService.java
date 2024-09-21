package com.numan947.toolrent.common;

import com.numan947.toolrent.tool.Tool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    @Value("${application.file.uploads.photos-directory}")
    private String fileUploadPath;


    public String saveFile(
            @NonNull MultipartFile sourceFile, @NonNull Long toolId, @NonNull String userId) {

        final String fileUploadSubPath = "users" + File.separator + userId + File.separator + "tools" + File.separator + toolId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile sourceFile, @NonNull String fileUploadSubPath) {
        final String realUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        final File uploadDirectory = new File(realUploadPath);
        if (!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
            log.warn("Could not create directory for file upload");
            return null;
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());

        // ./uploads/users/1/1234567890.jpg
        String targetFilePath = realUploadPath + File.separator + UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to {}", targetFilePath);
        } catch (IOException e) {
            log.error("Could not save file", e);
            return null;
        }
        return targetFilePath;
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank() || originalFilename.isEmpty()) {
            return null;
        }
        final int lastIndexOf = originalFilename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return null;
        }
        return originalFilename.substring(lastIndexOf + 1).toLowerCase();
    }
}
