package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.Config.customUserDetails;
// <<<<<<< master
// import com.genkey.foodmgt.model.impl.Menu;
// import com.genkey.foodmgt.model.impl.Users;
// =======
// import com.genkey.foodmgt.model.impl.*;
// >>>>>>> master
import com.genkey.foodmgt.dto.foodByDays;
import com.genkey.foodmgt.model.impl.*;
import com.genkey.foodmgt.repository.dao.api.*;
import com.genkey.foodmgt.repository.dao.impl.TransactionDAOImpl;
import com.genkey.foodmgt.services.api.MenuService;

import com.genkey.foodmgt.services.api.TransactionService;
import com.genkey.foodmgt.services.impl.MenuServiceImpl;
import com.genkey.foodmgt.util.Days;
import com.genkey.foodmgt.util.MenuParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// <<<<<<< master
// =======
// import java.text.SimpleDateFormat;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// >>>>>>> master
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j


@Controller
@RequestMapping
public class AdminMenuController {

    @Autowired
    ocrDAO ocrDAO;

    @Autowired
    DaysDAO daysDAO;

    @Autowired
    TransactionRepository repo;
    @Autowired
    TransactionService transactionService;
    @Autowired
    private MenuService menuService;

    @Autowired
    UserDAO userRepository;

    @Autowired
    TransactionDAOImpl transactionDAOImpl;

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    MenuServiceImpl service;


    @GetMapping("/admin/adminCreateMenu")
        public String DisplayAdminMenuPage(){
            return "adminmenuCreate";
        }


    @GetMapping("/Do")
    public String Done(Model model){
        return "Request";
    }

    @PostMapping("/saveMenu")
    public String saveMenu( @AuthenticationPrincipal customUserDetails cust, @RequestParam("orderPosition")
    String orderPosition,@RequestParam("dayPosition")
                            int dayPosition, @RequestParam("time") int time ,Date deliveryDate){

        if (time==6){
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
            deliveryDate=java.sql.Date.valueOf(tomorrow);
        } else if (time==7) {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
            deliveryDate=java.sql.Date.valueOf(tomorrow);
        } else if (time==8) {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
            deliveryDate=java.sql.Date.valueOf(tomorrow);
        } else if (time==9) {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
            deliveryDate=java.sql.Date.valueOf(tomorrow);
        } else if (time==10) {
            LocalDate today = LocalDate.now();
            LocalTime localTime = LocalTime.NOON;
            if (LocalTime.now().isAfter(localTime)){
                LocalDate tomorrow = today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                deliveryDate=java.sql.Date.valueOf(tomorrow);
                System.out.println("Order placed for next week friday");
                System.out.println(tomorrow);
            }else {
                LocalDate tomorrow = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
                deliveryDate=java.sql.Date.valueOf(tomorrow);
                System.out.println("Order placed for this friday");
                System.out.println(tomorrow);
            }

        }else {
            System.out.println("no date");
        }
        String user = cust.getUsername();
        ocrDatabase ocr = ocrDAO.findById("212").orElse(null);
        LocalDate date = ocr.getUploadDate();
        Users users = userRepository.GetByUsername(user).orElse(null);
        Food_Order order = transactionService.parseFoodOrder(dayPosition,orderPosition,users,deliveryDate);
        repo.save(order);
        return "redirect:/";
    }

    @PostMapping("/specialOrder")
    public String specialOrder( @RequestParam("username") String username, @RequestParam("orderPosition")
    String orderPosition,@RequestParam("dayPosition")
                            int dayPosition,@RequestParam("deliveryDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date deliveryDate, Model model){

        ocrDatabase ocr = ocrDAO.findById("212").orElse(null);
        LocalDate date = ocr.getUploadDate();
        Users user = userRepository.retrieveUsername(username);
        String name = user.getUsername();
        Users users = userRepository.GetByUsername(name).orElse(null);
        Food_Order order = transactionService.parseFoodOrder(dayPosition,orderPosition,users,deliveryDate);
        repo.save(order);
        return "redirect:/admin/admindashboard";
    }

    @GetMapping("/")
    public String createMenu(Model model){
        try {
            List<Menu> gimme = menuDAO.findAllByStatus("Available");
            if (gimme.isEmpty()){
                System.out.println("menu not uploaded yet");
            }else {
                StringBuilder fb = new StringBuilder();
                for (Menu fieldData : gimme) {
                    fb.append(fieldData.getWeekDays().getDays()).append("\n").append(fieldData.getFood()).append("\n");
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
                model.addAttribute("OCR", dayMenuMap);
            }
        }catch (Exception e){
            System.out.println("no ocr text present");
        }


        try {
            List<Menu> gimme = menuDAO.findAllByStatus("Available");
            if (gimme.isEmpty()) {
                System.out.println("menu not uploaded yet");
            } else {
                WeekDays days0 = daysDAO.findWeekDaysById("0");
                WeekDays days1 = daysDAO.findWeekDaysById("1");
                WeekDays days2 = daysDAO.findWeekDaysById("2");
                WeekDays days3 = daysDAO.findWeekDaysById("3");
                WeekDays days4 = daysDAO.findWeekDaysById("4");
                List<Menu> monday = menuDAO.findAllByStatusAndAndWeekDays("Available",days0);
                List<Menu> tuesday = menuDAO.findAllByStatusAndAndWeekDays("Available",days1);
                List<Menu> wednesday = menuDAO.findAllByStatusAndAndWeekDays("Available",days2);
                List<Menu> thursday = menuDAO.findAllByStatusAndAndWeekDays("Available",days3);
                List<Menu> friday = menuDAO.findAllByStatusAndAndWeekDays("Available",days4);
                model.addAttribute("monday", monday);
                model.addAttribute("tuesday", tuesday);
                model.addAttribute("wednesday", wednesday);
                model.addAttribute("thursday", thursday);
                model.addAttribute("friday", friday);
            }
        }catch (Exception e){
            System.out.println("no date found");
        }

        Food_Order menu = new Food_Order();

        model.addAttribute("menu", menu);
        return "menu";
    }

    @GetMapping("/editOrder/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model){
        Optional<Food_Order> menu = null;
        try {
            menu = menuService.getOrderById(id);
        String dayPosition = menu.get().getMenuitem().getWeekDays().getId();
        ocrDatabase ocr = ocrDAO.findById("212").orElse(null);
        LocalDate date = ocr.getUploadDate();
        if (dayPosition.equals("0")){
            WeekDays days3 = daysDAO.findWeekDaysById("0");
            model.addAttribute("day",menuDAO.moon(days3));
        } else if (dayPosition.equals("1")) {
            WeekDays days3 = daysDAO.findWeekDaysById("1");
            model.addAttribute("day",menuDAO.moon(days3));
        } else if (dayPosition.equals("2")) {
            WeekDays days3 = daysDAO.findWeekDaysById("2");
            model.addAttribute("day",menuDAO.moon(days3));
        } else if (dayPosition.equals("3")) {
            WeekDays days3 = daysDAO.findWeekDaysById("3");
            model.addAttribute("day",menuDAO.moon(days3));
        } else if (dayPosition.equals("4")) {
            WeekDays days3 = daysDAO.findWeekDaysById("4");
            model.addAttribute("day",menuDAO.moon(days3));
        }
        model.addAttribute("menu",menu);
        }catch (Exception ex){
            System.out.println("no food found for update page");
        }
        model.addAttribute("menu",menu);
        return "editMenu";
    }

    @PostMapping("/updateMenu")
    public String updateMenu(Food_Order food_order, String id){
        Food_Order existingOrder = repo.findById(id).orElse(null);
        existingOrder.setMenuitem(food_order.getMenuitem());
        repo.save(existingOrder);
        return "redirect:/admin/adminmenu";
    }

    //Get order for a given time (Range)
   /* @GetMapping("/search")
    public String Search(Model model, Date startD, Date endD){
       // Date start = new Date();
        //Date end = new Date();
      //  end= new Date();
       //start= new Date();
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

       // List<Menu> list = transactionDAOImpl.retrieveAllTransactionsForSpecificPeriod();
      //  start = new SimpleDateFormat("yyyy-MM-dd");
       // end = new SimpleDateFormat("yyyy-MM-dd");

     //  List<Menu> list = menuDAO.FindByCreatedBetween(start, end);
       // model.addAttribute("list", list);
        //log.info("here" + list);

       // LocalDateTime start = LocalDateTime();
        return "search";
    }
*/
    @GetMapping("/deleteOrder/{id}")
    public String deleteFoodOrder(
            @PathVariable("id") String id,
            RedirectAttributes attributes
    ){
        menuService.deleteOrder(id);
        attributes.addAttribute("message", "Menu with Id : '"+id+"' is removed successfully!");
        return "redirect:/profile";
    }



}
