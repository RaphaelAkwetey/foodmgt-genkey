package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.dto.FileInfo;
import com.genkey.foodmgt.dto.MealEdit;
import com.genkey.foodmgt.dto.requestBody;
import com.genkey.foodmgt.model.impl.FileModel;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.ocrDatabase;
import com.genkey.foodmgt.repository.dao.api.FileDAO;
import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import com.genkey.foodmgt.repository.dao.api.ocrDAO;
import com.genkey.foodmgt.services.api.ImageService;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.services.api.ocrService;
import com.genkey.foodmgt.services.impl.MailService;
import com.genkey.foodmgt.services.impl.MenuServiceImpl;
import com.genkey.foodmgt.services.impl.ocrServiceImpl;
import com.genkey.foodmgt.util.Converter;
import com.genkey.foodmgt.util.MenuParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DownloadFileController {



    @Autowired
    ocrDAO ocrDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    MailService mailService;

    @Autowired
    ocrService ocrServices;

    @Autowired
    ocrServiceImpl ocrImpl;

    @Autowired
    FileDAO fileRepository;
    @Autowired
    ImageService imageService;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuServiceImpl service;

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    @Autowired
    Converter converter;



    /*
     * Retrieve Files' Information
     */
    @GetMapping("/admin/adminmenu")
    public String getListFiles(Model model,@AuthenticationPrincipal customUserDetails cust) {
        List<FileInfo> fileInfos = fileRepository.findAll().stream().map(
                        fileModel -> {
                            String filename = fileModel.getName();
                            String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
                                    "downloadFile", fileModel.getName()).build().toString();
                            Long id = fileModel.getId();
                            return new FileInfo(filename,url,id);
                        }
                )
                .collect(Collectors.toList());

        try {
            ocrDatabase ocr = ocrDAO.findById("212").orElse(null);
            String text = ocr.getText();
            String[] menuLines = text.split("\n");

            //Parse OCR Text in menuMap
            MenuParser menuParser = new MenuParser(menuLines);
            menuParser.parseListToMap();
            Map<String, List<String>> map = service.O_C_R();
            //string array to model as an attribute
            model.addAttribute("OCR", map);
        }catch (Exception e){
            System.out.println("no ocr text present");
        }
        List<MealEdit> Monday = menuDAO.retrieveSpecifiedMeals("Monday");
        List<MealEdit> Tuesday = menuDAO.retrieveSpecifiedMeals("Tuesday");
        List<MealEdit> Wednesday = menuDAO.retrieveSpecifiedMeals("Wednesday");
        List<MealEdit> Thursday = menuDAO.retrieveSpecifiedMeals("Thursday");
        List<MealEdit> Friday = menuDAO.retrieveSpecifiedMeals("Friday");

        //Todo: Chris you can find everything here for the edit card.
        model.addAttribute("Monday",Monday);
        model.addAttribute("Tuesday",Tuesday);
        model.addAttribute("Wednesday",Wednesday);
        model.addAttribute("Thursday",Thursday);
        model.addAttribute("Friday",Friday);

        String role = cust.getAuthorities().toString();
        model.addAttribute("role",role);
        model.addAttribute("files", fileInfos);
        return "adminmenu";
    }

    @RequestMapping(value="/deletePic", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Long id) {
        fileRepository.deleteById(id);
        return "redirect:/admin/adminmenu";
    }

    @PostMapping("/broadcastUploadedMenu")
    public String broadcastUploadedMenu(){
        menuDAO.setMenuToAvailable(LocalDate.now());
        mailService.sendEmailUsingVelocityTemplate();
        return "redirect:/admin/adminmenu";
    }

    @PostMapping("/broadcastUpdatedMenu")
    public String broadcastUpdatedMenu(){
        mailService.sendUpdatedMenuEmails();
        return "redirect:/admin/adminmenu";
    }


    //mailService.sendEmailUsingVelocityTemplate();

    @PostMapping("/KillSwitch")
    public String DeleteOcrAndMenu(){
        ocrDatabase db = ocrDAO.findById("212").orElse(null);
        LocalDate date = db.getUploadDate();
        menuDAO.deleteByUploadDate(date);
        ocrDAO.deleteAllByUploadDate(date);
        return "redirect:/admin/adminmenu";
    }

    @PostMapping("/confirm")
    public String MenuConfirmation(){
        ocrDatabase db = ocrDAO.findById("212").orElse(null);
        db.setDisplay(true);
        ocrDAO.save(db);
        mailService.sendEmailUsingVelocityTemplate();
        return "redirect:/admin/adminmenu";
    }

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        FileModel file = fileRepository.findByName(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getPic());
    }

    @Transactional
    @PostMapping("/upload")
    public String extract(Model model, MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();

        //Retrieve the corresponding image from the db
        //FileModel fileModel = imageService.findByName(filename);
        converter.loadFile(file);

        String menuOCR="";

        try {
            //split the file name by '.' and save the second part as the image format
            String format = filename.split("\\.")[1];

            //get the byte stream of the image
            byte[] pic = converter.getFiles(filename);

            //write the byte stream into a file
            ByteArrayInputStream bis = new ByteArrayInputStream(pic);
            BufferedImage bImage = ImageIO.read(bis);
            if (bImage != null) {
                File menuImage = new File("menuImage.png");
                ImageIO.write(bImage, format, menuImage);

                //retrieve the url of the generated file
                URL myURL = menuImage.toURI().toURL();
                String url = myURL.toString();

                //pass url of image to Google OCR service
                menuOCR = this.cloudVisionTemplate.extractTextFromImage(this.resourceLoader.getResource(url));
                System.out.println(menuOCR);
            } else {
                // Image is null, fallback to PDF extraction
                ByteArrayInputStream inputStream = new ByteArrayInputStream(pic);
                PDDocument pd = PDDocument.load(inputStream);
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(pd);
                pd.close();
                menuOCR = text.trim().replaceAll("\n+", "\n");
                String[] menuLines = menuOCR.split("\n");
                StringBuilder sb = new StringBuilder();
                for (String line : menuLines) {
                    String trimmedLine = line.trim();
                    if (!trimmedLine.isEmpty()) {
                        sb.append(trimmedLine).append("\n");
                    }
                }
                String formattedMenuOCR = sb.toString().trim();
                menuOCR = formattedMenuOCR;
                System.out.println(menuOCR);
            }
        } catch (IOException io){
            System.out.println("Error extracting text!!!");
        }
        //String menuOCR = String.valueOf(this.cloudVisionTemplate.analyzeImage(this.resourceLoader.getResource(url), Feature.Type.DOCUMENT_TEXT_DETECTION));



        try {
            LocalDate date = LocalDate.now();
            ocrDatabase db = ocrDAO.findById("212").orElse(null);
            System.out.println("trying to save text");
            db.setText(menuOCR);
            System.out.println("saved text");
            db.setUploadDate(date);
            ocrDAO.save(db);
        }catch (Exception e){
            LocalDate date = LocalDate.now();
            ocrDatabase ocr1 = new ocrDatabase();
            ocr1.setId("212");
            System.out.println("trying to save text");
            ocr1.setText(menuOCR);
            System.out.println("saved text");
            ocr1.setUploadDate(date);
            ocrDAO.save(ocr1);
        }

        //split string result into a string array by newline regex '\n'
        System.out.println("splitting text - initial");
        String[] menuLines = menuOCR.split("\n");
        System.out.println("splitting text");

        //Parse OCR Text in menuMap
        System.out.println("persisting text - initial");
        MenuParser menuParser = new MenuParser(menuLines);
        menuParser.parseListToMap();
        System.out.println("persisting text");
        menuService.persistMenuMap(menuParser.parseListToMap());
        System.out.println("persisting text done");

        Map<String, List<String>> map = service.O_C_R();
        //string array to model as an attribute
        model.addAttribute("OCR", map);
        Menu menu = new Menu();
        model.addAttribute("menu", menu);



        if(filename != null){
            converter.deleteAll(filename);
        } else {
            System.out.println("no file available for deletion");
        }

        ocrDatabase db = ocrDAO.findById("212").orElse(null);
        db.setDisplay(true);
        ocrDAO.save(db);
        mailService.sendEmailUsingVelocityTemplate();
        //display the results.
        //ConfirmMenu

        //MenuConfirmation();

        return "redirect:/admin/adminmenu";
    }


    @Transactional
    @PostMapping("/ManualUpload")
    public String extraction(Model model, @RequestBody(required = false) final List<requestBody> fieldDataList) throws IOException {


        String menuOCR="";

        try {
            StringBuilder fb = new StringBuilder();
            for (requestBody fieldData : fieldDataList) {
                fb.append(fieldData.getDay()).append("\n").append(fieldData.getValue()).append("\n");
            }

            String[] lines = fb.toString().split("\n");
            Map<String, List<String>> dayMenuMap = new LinkedHashMap<>();
            List<String> currentMenu = null;

            for (String line : lines) {
                if (line.equals("Monday") || line.equals("Tuesday") || line.equals("Wednesday") || line.equals("Thursday")
                        || line.equals("Friday")) {
                    currentMenu = dayMenuMap.computeIfAbsent(line, k -> new ArrayList<>());
                } else if (currentMenu != null) {
                    currentMenu.add(line);
                }
            }

            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, List<String>> entry : dayMenuMap.entrySet()) {
                String day = entry.getKey();
                List<String> dayMenu = entry.getValue();

                sb.append(day).append("\n");
                for (String menu : dayMenu) {
                    sb.append(menu).append("\n");
                }
            }


            System.out.println(sb.toString());
            menuOCR = sb.toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //String menuOCR = String.valueOf(this.cloudVisionTemplate.analyzeImage(this.resourceLoader.getResource(url), Feature.Type.DOCUMENT_TEXT_DETECTION));


        try {
            LocalDate date = LocalDate.now();
            ocrDatabase db = ocrDAO.findById("212").orElse(null);
            System.out.println("trying to save text");
            db.setText(menuOCR);
            System.out.println("saved text");
            db.setUploadDate(date);
            ocrDAO.save(db);
        }catch (Exception e){
            LocalDate date = LocalDate.now();
            ocrDatabase ocr1 = new ocrDatabase();
            ocr1.setId("212");
            System.out.println("trying to save text");
            ocr1.setText(menuOCR);
            System.out.println("saved text");
            ocr1.setUploadDate(date);
            ocrDAO.save(ocr1);
        }

        //split string result into a string array by newline regex '\n'
        System.out.println("splitting text - initial");
        String[] menuLines = menuOCR.split("\n");
        System.out.println("splitting text");

        //Parse OCR Text in menuMap
        System.out.println("persisting text - initial");
        MenuParser menuParser = new MenuParser(menuLines);
        menuParser.parseListToMap();
        System.out.println("persisting text");
        menuService.persistMenuMap(menuParser.parseListToMap());
        System.out.println("persisting text done");

        //string array to model as an attribute
        model.addAttribute("OCR", menuParser.parseListToMap());
        Menu menu = new Menu();
        model.addAttribute("menu", menu);
        ocrDatabase db = ocrDAO.findById("212").orElse(null);
        db.setDisplay(false);
        ocrDAO.save(db);
        db.setDisplay(true);
        ocrDAO.save(db);
        mailService.sendEmailUsingVelocityTemplate();
        //display the results.
        return "redirect:/admin/adminmenu";
    }
}
