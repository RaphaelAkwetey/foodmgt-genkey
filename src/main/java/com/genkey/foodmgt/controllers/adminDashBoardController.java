package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.customUserDatailService;
import com.genkey.foodmgt.dto.OrderResponse;
import com.genkey.foodmgt.dto.OrderSummary;
import com.genkey.foodmgt.dto.SpecificUserOrders;
import com.genkey.foodmgt.dto.foodByDays;
import com.genkey.foodmgt.model.impl.*;
import com.genkey.foodmgt.repository.dao.api.*;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.util.SameDayOfWeek;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping
@Slf4j
public class adminDashBoardController {

    @Autowired
    private MenuService menuService;


    @Autowired
    DaysDAO daysDAO;

    @Autowired
    ocrDAO ocrDAO;

    @Autowired
    MetaDAO metaDAO;


    @Autowired
    private UserDAO usersDAO;

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    TransactionRepository transactionRepository;


    @GetMapping("/admin/admindashboard")
    public String AdminDashboard(Model model, @RequestParam(value = "delivery",required = false) LocalDate delivery,@AuthenticationPrincipal customUserDetails cust
    , @RequestParam(value = "stats",required = false) Status stats,
                                 @RequestParam(value = "sd",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate sd) {
        String role = cust.getAuthorities().toString();
        LocalDate today = LocalDate.now();
        List<OrderResponse> users = usersDAO.getFirstnameOfUsers();

        int currentMon = today.getMonthValue();
        int currentYear = today.getYear();
        LocalDate fDay = LocalDate.of(currentYear, currentMon, 1);
        LocalDate lDay = LocalDate.of(currentYear, currentMon, 26);
        if (today.getDayOfMonth() >= 27) {
            fDay = LocalDate.of(currentYear, currentMon, 27);
            lDay = fDay.plusDays(26);
            System.out.println("ichigo");
        } else if (today.getDayOfMonth() <= 26) {
            fDay = fDay.minusMonths(1).withDayOfMonth(27);
            lDay = LocalDate.of(currentYear, currentMon, 26);
            System.out.println("bankai");
        }
        Double bill = transactionRepository.TotalMonthlyBill(Date.valueOf(fDay), Date.valueOf(lDay));
        if (bill != null) {
            model.addAttribute("bill", bill);
        } else {
            model.addAttribute("bill", 0.0);
        }



        LocalDate devDate = LocalDate.now();
        LocalDate tomorrow =devDate.plusDays(1);
        LocalDate yesterday = devDate.minusDays(1);
        LocalDate next = today.plusDays(2);
        LocalDate to = LocalDate.now();
        /*try {

            if (stats.equals("PENDING")) {
                stats = Status.PENDING;
            } else if (stats.equals("RECEIVED")) {
                stats = Status.RECEIVED;
            } else {
                stats = null;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }*/

        List<OrderResponse> orders;
        if (stats!=null){
            orders = transactionRepository.filterAllOrders(stats,yesterday,tomorrow);
            Double total = transactionRepository.TotalSumOfPendingOrders(yesterday, tomorrow);
            model.addAttribute("total", total);
            if (total == null) {
                model.addAttribute("total", 0.0);
            }
            log.info("bill is out");
        } else {
            if (devDate.equals(to)) {
                orders = transactionRepository.getAllPendingOrdersAndUser(yesterday, tomorrow);
                Double total = transactionRepository.TotalSumOfPendingOrders(yesterday, tomorrow);
                model.addAttribute("total", total);
                if (total == null) {
                    model.addAttribute("total", 0.0);
                }
                log.info("bill is out");
            } else {
                orders = transactionRepository.getAllPendingOrdersAndUser(today, next);
                Double total = transactionRepository.TotalSumOfPendingOrders(today, next);
                model.addAttribute("total", total);
                if (total == null) {
                    model.addAttribute("total", 0.0);
                }
                log.info("bill is out");
            }
        }

        LocalDate monday1 = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate tuesday1 = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
        LocalDate wednesday1 = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
        LocalDate thursday1 = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        //LocalDate friday1 = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        // fixed the time complexity.
        LocalDate friday1 = null;
        try {
            LocalDate space = today.with(new SameDayOfWeek(DayOfWeek.FRIDAY));
        if (today.isEqual(space)){
            friday1 = today.with(new SameDayOfWeek(DayOfWeek.FRIDAY));
            System.out.println("same friday");

        }else {
            friday1 = today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            System.out.println("next friday");
        }
        }catch (Exception e){
            System.out.println("no exceptions");
        }
        List<OrderResponse> odd = transactionRepository.getAllPendingOrdersAndUsers(monday1);
        List<OrderResponse> odd2 = transactionRepository.getAllPendingOrdersAndUsers(wednesday1);
        List<OrderResponse> odd4 = transactionRepository.getAllPendingOrdersAndUsers(friday1);
        List<OrderResponse> odd1 = transactionRepository.getAllPendingOrdersAndUsers(tuesday1);
        List<OrderResponse> odd3 = transactionRepository.getAllPendingOrdersAndUsers(thursday1);
        model.addAttribute("odd",odd);
        model.addAttribute("odd1",odd1);
        model.addAttribute("odd2",odd2);
        model.addAttribute("odd3",odd3);
        model.addAttribute("odd4",odd4);
        model.addAttribute("orders",orders);
        List<OrderSummary> allSummary; //transactionRepository.getTodaySummary(yesterday,tomorrow);
        if (sd == null){
            allSummary = transactionRepository.getTodaySummary(yesterday,tomorrow);
        } else {
            allSummary = transactionRepository.getTodaySummaryByDate(sd);
        }
        /*Double bill = transactionRepository.TotalMonthlyBill(fDay,lDay);
        model.addAttribute("bill",bill);
        if(bill==null){
            model.addAttribute("bill",0.0);
        }*/


           /* Double total = transactionRepository.TotalSumOfPendingOrders(today,next);
            model.addAttribute("total",total);
            if (total==null){
                model.addAttribute("total",0.0);
            }
            log.info("bill is out");*/

        try {
            ocrDatabase ocr = ocrDAO.findById("212").orElse(null);
            LocalDate date = ocr.getUploadDate();
            List<foodByDays> brunch = menuDAO.sun();
            model.addAttribute("brunch",brunch);
        }catch (Exception e){
            System.out.println("not fun at all");
        }

        model.addAttribute("role",role);
        int count;
        if (devDate.equals(to)) {
            count = transactionRepository.pendingOrders(yesterday, tomorrow);
        } else {
            count = transactionRepository.pendingOrders(today, next);
        }

        try {
            ocrDatabase ocr = ocrDAO.findById("212").orElse(null);
            LocalDate date = ocr.getUploadDate();
            WeekDays days = daysDAO.findWeekDaysById("0");
            WeekDays days1 = daysDAO.findWeekDaysById("1");
            WeekDays days2 = daysDAO.findWeekDaysById("2");
            WeekDays days3 = daysDAO.findWeekDaysById("3");
            WeekDays days4 = daysDAO.findWeekDaysById("4");
            List<foodByDays> monday = menuDAO.moon(days);
            List<foodByDays> tuesday = menuDAO.moon(days1);
            List<foodByDays> wednesday = menuDAO.moon(days2);
            List<foodByDays> thursday = menuDAO.moon(days3);
            List<foodByDays> friday = menuDAO.moon(days4);
            model.addAttribute("monday",monday);
            model.addAttribute("tuesday",tuesday);
            model.addAttribute("wednesday",wednesday);
            model.addAttribute("thursday",thursday);
            model.addAttribute("friday",friday);
        }catch (Exception e){
            System.out.println("no date found");
        }
        model.addAttribute("count",count);
        model.addAttribute("allSummary", allSummary);
        model.addAttribute("users",users);

        return "admindashboard";
    }

    @GetMapping("/processOrder/{id}")
    public String UpdateOrder(@PathVariable("id") String id) {
        menuService.processOrds(id);
        return "redirect:/admin/admindashboard";
    }


    @GetMapping("/processOrder2/{id}")
    public String UpdateOrder2(@PathVariable("id") String id) {
        menuService.processOrdss(id);
        return "redirect:/admin/admindashboard";
    }

    @GetMapping("/DeleteOrderFromDashboard/{id}")
    public String DeleteOrderDash(@PathVariable("id") String id) {
        Food_Order food_order = transactionRepository.findById(id).orElse(null);
        transactionRepository.delete(food_order);
        return "redirect:/admin/admindashboard";
    }

    // getting user specific orders listed
    @GetMapping("/profile")
    public String SpecificUserOrders(Model model, @AuthenticationPrincipal customUserDetails cust,Meta meta,@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size
    ){
        String username = cust.getUsername();
        String role = cust.getAuthorities().toString();
        String pass = cust.getPassword();
        System.out.println(pass);
        LocalDate devDate = LocalDate.now();
        LocalDate today = LocalDate.now();
        LocalDate fDay =  today.withDayOfMonth(1);
        LocalDate lDay =  today.withDayOfMonth(today.lengthOfMonth());
        Users user = usersDAO.GetByUsername(username).orElse(null);
        List<SpecificUserOrders> userSpecific = transactionRepository.getOnlyPendingSpecificUserOrders(user);
        Pageable pageable = PageRequest.of(page, size);
        Page<SpecificUserOrders> userSpecifics = transactionRepository.getOnlyAcceptedSpecificUserOrders(user,pageable);
        List<Integer> pageNumbers = IntStream.rangeClosed(0, userSpecifics.getTotalPages() - 1)
                .boxed()
                .collect(Collectors.toList());
        int totalPages = (int) Math.ceil((double) userSpecifics.getTotalElements() / size);

        Double MonthlySpending = transactionRepository.TotalSumOfUserMonthlySpending(user.getId(),fDay,lDay);
        model.addAttribute("MonthlySpending",MonthlySpending);

        if (MonthlySpending==null){
            model.addAttribute("MonthlySpending",0.0);
        }

        Double Msalary = transactionRepository.TotalSumOfUserMonthlySpending(user.getId(),fDay,lDay);

        if (Msalary==null){
            model.addAttribute("Msalary",0.0);
        } else {
            model.addAttribute("Msalary",Msalary/2);
        }

        System.out.println(MonthlySpending);

        //this is to display the cap and credit on the profile page
        try{
            Meta meta4 = metaDAO.findById("345").orElse(null);
            double meta1 = meta4.getCap();
            double meta2 = meta4.getCredit();
            double meta3 = meta4.getCurrentFoodPrice();
            model.addAttribute("meta1", meta1);
            model.addAttribute("meta2", meta2);
            model.addAttribute("meta3", meta3);
            log.info("cap and credit display was successful");
        }catch (Exception e){
            double meta1 = meta.getCap();
            double meta2 = meta.getCredit();
            double meta3 = meta.getCurrentFoodPrice();
            model.addAttribute("meta1", meta1);
            model.addAttribute("meta2", meta2);
            model.addAttribute("meta3", meta3);
            log.info("no cap or credit found");
        }
        model.addAttribute("userSpecific",userSpecific);
        model.addAttribute("userSpecifics",userSpecifics);
        model.addAttribute("role",role);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("totalPages", totalPages);

        return "profile";
    }

    @PostMapping("/MarkAsAcceptedOne")
    public String Some(Menu menu) {
        LocalDate devDate = LocalDate.now();
        LocalDate monday1 = devDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        List<OrderResponse> order = transactionRepository.getAllPendingOrdersAndUsers(monday1);
        menuService.processOrders(order);
        log.info("All orders are accepted one");
        return "redirect:/admin/admindashboard";
    }

    @PostMapping("/MarkAsAcceptedTwo")
    public String Some1(Menu menu) {
        LocalDate devDate = LocalDate.now();
        LocalDate tuesday = devDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
        List<OrderResponse> order = transactionRepository.getAllPendingOrdersAndUsers(tuesday);
        menuService.processOrders(order);
        log.info("All orders are accepted two");
        return "redirect:/admin/admindashboard";
    }

    @PostMapping("/MarkAsAcceptedThree")
    public String Some2(Menu menu) {
        LocalDate devDate = LocalDate.now();
        LocalDate wednesday = devDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
        List<OrderResponse> order = transactionRepository.getAllPendingOrdersAndUsers(wednesday);
        menuService.processOrders(order);
        log.info("All orders are accepted three");
        return "redirect:/admin/admindashboard";
    }

    @PostMapping("/MarkAsAcceptedFour")
    public String Some4(Menu menu) {
        LocalDate devDate = LocalDate.now();
        LocalDate thursday = devDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        List<OrderResponse> order = transactionRepository.getAllPendingOrdersAndUsers(thursday);
        menuService.processOrders(order);
        log.info("All orders are accepted four");
        return "redirect:/admin/admindashboard";
    }

    @PostMapping("/MarkAsAcceptedFive")
    public String Some5(Menu menu) {
        LocalDate devDate = LocalDate.now();
        LocalDate friday = devDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        List<OrderResponse> order = transactionRepository.getAllPendingOrdersAndUsers(friday);
        menuService.processOrders(order);
        log.info("All orders are accepted five");
        return "redirect:/admin/admindashboard";
    }

}
