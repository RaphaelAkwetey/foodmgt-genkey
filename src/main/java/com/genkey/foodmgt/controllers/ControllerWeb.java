package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.dto.*;
import com.genkey.foodmgt.model.impl.*;
import com.genkey.foodmgt.repository.dao.api.MetaDAO;
import com.genkey.foodmgt.repository.dao.api.ReportDAO;
import com.genkey.foodmgt.repository.dao.api.TransactionRepository;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.services.api.UserService;
import com.genkey.foodmgt.services.impl.MonthlyReportService;
import com.genkey.foodmgt.services.impl.ReportService;
import com.genkey.foodmgt.services.impl.UserServiceImpl;
import com.genkey.foodmgt.services.impl.dailyReports;
import com.lowagie.text.DocumentException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author david
 */


@Controller
public class ControllerWeb {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MonthlyReportService monthlyReportService;

    @Autowired
    MetaDAO metaDAO;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserDAO service;

    @Autowired
    MenuService menuService;

    @Autowired
    ReportDAO reportDAO;


    @GetMapping("/login")
    public String greeting( Model model) {
        return "login";
    }

    @GetMapping("/edituser")
    public String editDbfood(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "editDbfood";
    }

//    @GetMapping("/menu")
//    public String menu(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "menu";
//    }

//    @GetMapping("/profile")
//    public String profile(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "profile";
//    }

    @GetMapping("/reportcenter")
    public String reportCenter(Model model, @RequestParam(value = "delivery",required = false)LocalDate delivery,@RequestParam(value = "username",required = false) String username, @RequestParam(value = "sd",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate sd,
                               @RequestParam(value = "ed",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ed,@RequestParam(value = "start",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
                               @RequestParam(value = "end",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end, @RequestParam(value = "firstname",required = false) String firstname,@AuthenticationPrincipal customUserDetails cust,
                               @RequestParam(value = "std",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate std, @RequestParam(value = "etd",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate etd,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "5") int size) {
        List<OrderResponse> users = service.getFirstnameOfUsers();
        LocalDate devDate = LocalDate.now();
        LocalDate dev = devDate.minusDays(4);
        List<OrderResponse> UserOrders; //= transactionRepository.getAllAcceptedOrdersAndUser(firstname,start,end);
        Double UserTotal;
        if (firstname!=null){
            UserOrders = transactionRepository.getAllAcceptedOrdersAndUser(firstname,start,end);
            UserTotal = transactionRepository.getTotalCostOfAllAcceptedOrdersAndUser(firstname,start,end);
        }else {
            UserOrders = transactionRepository.getAllAcceptedOrdersAndUser2(start,end);
            UserTotal = transactionRepository.getTotalCostOfAllAcceptedOrdersAndUser2(start,end);
        }
        Pageable pageable = PageRequest.of(page, size);

        // Get a page of results using the Pageable object
        Page<Summary> PendingOrdersToBeReceivedPage = transactionRepository.receiveSpecificOrders(PageRequest.of(page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", PendingOrdersToBeReceivedPage.getTotalPages());
        model.addAttribute("totalItems", PendingOrdersToBeReceivedPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("UserTotal", UserTotal);


        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);
        List<Summary> SummaryForToday = transactionRepository.getSpecificSummary(sd,ed);
        //List<Summary> PendingOrdersToBeReceived = transactionRepository.receiveSpecificOrders();
        model.addAttribute("Unconfirmed",PendingOrdersToBeReceivedPage.getContent());
        Integer food = transactionRepository.TotalNumOfFood(sd,ed);
        model.addAttribute("SummaryForToday",SummaryForToday);
        String role = cust.getAuthorities().toString();
        model.addAttribute("role",role);
        model.addAttribute("food",food);
        model.addAttribute("users",users);
        model.addAttribute("UserOrders",UserOrders);
        return "reportcenter";
    }

    @GetMapping("/getSummary")
    public String getSpecificSummary(Model model, @RequestParam(value = "sd",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate sd,
                                     @RequestParam(value = "ed",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ed){
    LocalDate today = LocalDate.now();
    LocalDate yesterday = today.minusDays(1);
    LocalDate tomorrow = today.plusDays(1);
    List<Summary> SummaryForToday = transactionRepository.getSpecificSummary(sd,ed);
    model.addAttribute("SummaryForToday",SummaryForToday);
    return "reportcenter";
    }
   /* @PostMapping("/report")
    public void generateReport(HttpServletResponse response) throws JRException, IOException {
        JasperPrint jasperPrint = null;

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"MonthlyBill.pdf\""));

        OutputStream out = response.getOutputStream();
        jasperPrint = reportService.exportReport();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        reportDAO.deleteAll();

    }*/

    /*@DeleteMapping("/DeleteOrder/{id}")
    public String deleteOrder(@PathVariable String id) {
        Optional<Food_Order> order = transactionRepository.findById(id);
        if (order.isPresent()) {
            transactionRepository.delete(order.get());
            return "redirect:/reportcenter";
        } else {
            return "error";
        }
    }*/

    /*@GetMapping("/processOrder3/{id}")
    public String UpdateOrder3(@PathVariable("id") String id) {
        menuService.processOrds(id);
        return "redirect:/reportcenter";
    }*/



    @GetMapping("/processOrder4/{id}")
    public String UpdateOrder4(@PathVariable("id") String id) {
        menuService.processOrdss(id);
        return "redirect:/reportcenter";
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response,@RequestParam(value = "day",required = false) String day) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate tuesday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
        LocalDate wednesday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
        LocalDate thursday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        LocalDate friday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=List of daily Pending Orders_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        if (day.equals("Monday")){
            List<FoodOrderDto> listUsers = transactionRepository.getAllOrdersForToday(monday);
            dailyReports exporter = new dailyReports(listUsers);
            exporter.export(response);
        } else if (day.equals("Tuesday")) {
            List<FoodOrderDto> listUsers = transactionRepository.getAllOrdersForToday(tuesday);
            dailyReports exporter = new dailyReports(listUsers);
            exporter.export(response);
        } else if (day.equals("Wednesday")) {
            List<FoodOrderDto> listUsers = transactionRepository.getAllOrdersForToday(wednesday);
            dailyReports exporter = new dailyReports(listUsers);
            exporter.export(response);
        } else if (day.equals("Thursday")) {
            List<FoodOrderDto> listUsers = transactionRepository.getAllOrdersForToday(thursday);
            dailyReports exporter = new dailyReports(listUsers);
            exporter.export(response);
        } else if (day.equals("Friday")) {
            List<FoodOrderDto> listUsers = transactionRepository.getAllOrdersForToday(friday);
            dailyReports exporter = new dailyReports(listUsers);
            exporter.export(response);
        }
    }

    // new reports
    @GetMapping("/report")
    public void exportToPDFTwo(HttpServletResponse response, @RequestParam(value = "sd",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate sd,
                               @RequestParam(value = "ed",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ed) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        LocalDate today = LocalDate.now();
        LocalDate yes = LocalDate.now();
        LocalDate ahead = yes.plusDays(1);
        LocalDate no = yes.minusDays(28);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Monthly Bill_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        monthlyReportService.CalcSpending(sd,ed);

        List<MonthlyReport> AllUsers = reportDAO.findMonthlyReportByLocalDate(today);
            ReportService exporter = new ReportService(AllUsers);
            exporter.export(response);
        reportDAO.deleteAll();

    }


    @RequestMapping("/getOneOrder")
    @ResponseBody
    public Optional<Food_Order> getOneOrder(String id){
        return menuService.getOrderById(id);
    }

   @PostMapping("/updateOrder")
    public String editFood(@RequestParam("id") String id, @RequestParam("food") String food, @AuthenticationPrincipal customUserDetails cust){
        String username = cust.getUsername();
        String  userId = userService.getIdByUsername(username);
        menuService.updateOrder(id,userId,food);
        return "redirect:/profile";
    }

    @GetMapping("/password")
    public String changePassword(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model){
        model.addAttribute("name",name);
        return "password";
    }

}