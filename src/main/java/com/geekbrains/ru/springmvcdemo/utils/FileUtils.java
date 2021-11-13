package com.geekbrains.ru.springmvcdemo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileUtils {

    public static final String staticPath = "data";

    public static Path saveProductImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        File pathFile = new File(staticPath);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }

        pathFile = new File(staticPath + File.separator +fileName);
        try {
            file.transferTo(pathFile);
            return Path.of(staticPath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
