package com.numan947.toolrent.common;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {
    public static byte[] readFileFromPath(String photoUrl) {
        if (photoUrl == null || photoUrl.isBlank() || photoUrl.isEmpty()) {
            return null;
        }
        try{
            return Files.readAllBytes(Paths.get(photoUrl));
        }catch (IOException e) {
            log.warn("Could not read file from path", e);
        }
        return null;
    }
}
