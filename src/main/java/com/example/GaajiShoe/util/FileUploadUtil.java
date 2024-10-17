package com.example.GaajiShoe.util;/*  gaajiCode
    99
    16/10/2024
    */

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadUtil {


        private final String UPLOAD_DIR = "/Users/gaaji/Downloads/testUpload/"; // Set your upload directory

        public String saveFile(MultipartFile file) throws IOException {
            // Ensure the upload directory exists
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create a path for the new file
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);

            // Return the path to the saved file
            return path.toString();
        }
}
