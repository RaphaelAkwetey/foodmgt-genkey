package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.model.impl.FileModel;
import com.genkey.foodmgt.repository.dao.api.FileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UploadImageController {

    @Autowired
    FileDAO fileRepository;

    @GetMapping("/form")
    public String index() {
        return "UploadForm";
    }

   /* @PostMapping("/upload")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile[] files, Model model) {
        List<String> fileNames = new ArrayList<String>();

        try {
            List<FileModel> storedFile = new ArrayList<FileModel>();

            for (MultipartFile file : files) {
                FileModel fileModel = fileRepository.findByName(file.getOriginalFilename());
                if (fileModel != null) {
                    // update new contents
                    fileModel.setPic(file.getBytes());
                } else {
                    fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                }

                fileNames.add(file.getOriginalFilename());
                storedFile.add(fileModel);
            }

            // Save all Files to database
            fileRepository.saveAll(storedFile);

            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames);
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }

        return "redirect:/admin/adminmenu";
    }*/
}
