package com.genkey.foodmgt.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class Converter {

    byte[] arr;

    private final Path root = Paths.get("Uploads");

    public void loadFile(MultipartFile file){
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public byte[] getFiles(String filename){
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            File f = new File(resource.getFile().getPath());
             Path path = Paths.get(f.getAbsolutePath());
              arr = Files.readAllBytes(path);

            // return parsedText;

        }catch (Exception ex){
            ex.getMessage();
            System.out.println("Error");
        }
        return arr;
    }

    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void deleteAll(String filename) throws IOException {
        Path file = root.resolve(filename);
        Files.deleteIfExists(file);
    }

    public void deleteBothFolderAndFile() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}
