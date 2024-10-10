package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.dto.MealEdit;
import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import com.genkey.foodmgt.repository.dao.api.TransactionRepository;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.services.impl.MailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


@RestController
@RequestMapping
@Slf4j
public class DeletionController {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    MailService mailService;

    @DeleteMapping("/DeleteOrder/{id}")
    public String deleteOrder(@PathVariable("id") String id) {
        Optional<Food_Order> order = transactionRepository.findById(id);
        if (order.isPresent()) {
            transactionRepository.delete(order.get());
            return "redirect:/reportcenter";
        } else {
            return "error";
        }
    }

    @GetMapping("/processOrder3/{id}")
    public String UpdateOrder3(@PathVariable("id") String id) {
        menuService.processOrds(id);
        return "redirect:/reportcenter";
    }

    @PostMapping("/editSingleMeal/{day}")
    public ResponseEntity<?> retrieve(@PathVariable("day") String day){

        try {
            List<MealEdit> meal = menuDAO.retrieveSpecifiedMeals(day);
            List<Map<String, String>> menu = new ArrayList<>();

            for (MealEdit m : meal) {
                Map<String, String> mealMap = new HashMap<>();
                mealMap.put("id", m.getId());
                mealMap.put("food", m.getFood());
                mealMap.put("day", m.getDays());
                mealMap.put("status", m.getStatus());
                menu.add(mealMap);
            }

            Map<String, List<Map<String, String>>> menuWrapper = new HashMap<>();
            menuWrapper.put("array", menu);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

// Convert the Map to JSON string
            String jsonString = gson.toJson(menuWrapper);

// Print the JSON string
            System.out.println(jsonString);
            return ResponseEntity.ok(jsonString);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/editedMenu")
    public ResponseEntity<?> acceptEditedMeal(@RequestBody Map<String, List<Map<String, String>>> menuWrapper) {
        try
        {
        List<Map<String, String>> menu = menuWrapper.get("array");
        int count = 0;
        Optional<Menu> menu1 = null;
        // Loop through and display each menu item
        for (Map<String, String> menuItem : menu) {
            String id = menuItem.get("id");
            String food = menuItem.get("food");
            String status = menuItem.get("status");

            if (count == 0) {
                menu1 = menuDAO.findById(id);// Store the first ID
            }
            if (id == null) {
                String menuIdString = menu1.get().getWeekDays().getId();
                int menuIdInt = Integer.parseInt(menuIdString);
                LocalDate range = menu1.get().getExpiryDate();


                Menu menu2 = new Menu(food, menu1.get().getMenuItemPosition(), menu1.get().getCost(), menuIdInt, menu1.get().getUploadDate(), menu1.get().getVendor(), "Available",range);
                menuDAO.save(menu2);
                System.out.println("new menu saved!!!");
            } else {
                menuDAO.editMenuIndividually(status, id);
                System.out.println("first meal edited: " + count++);
            }
        }
            mailService.sendUpdatedMenuEmails();
        log.info("updated menu email sent successfully.");
            return ResponseEntity.ok("Menu received successfully");
        }catch(Exception e){
            log.error("couldn't update menu");
            return ResponseEntity.ok("Menu not received successfully");
        }

        // Return a response

    }



}
