package com.genkey.foodmgt.controllers;


import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.model.impl.Meta;
import com.genkey.foodmgt.model.impl.PasswordResetToken;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.api.CreditLogDAO;
import com.genkey.foodmgt.repository.dao.api.MetaDAO;
import com.genkey.foodmgt.repository.dao.api.UserDAO;
import com.genkey.foodmgt.services.api.UserService;
import com.genkey.foodmgt.services.impl.MailService;
import com.genkey.foodmgt.services.impl.PasswordResetTokenService;
import com.genkey.foodmgt.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping
@Slf4j
public class AdminUsersController {

    @Autowired
    MetaDAO metaDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    CreditLogDAO creditLogDAO;

    @Autowired
    UserService service;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private MailService mailService;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/save")
    public String saveUser(
            @Valid Users users, BindingResult result, RedirectAttributes apply,Model model
    ) {
        Users existing = userDAO.findByEmailIgnoreCase(users.getEmail()).orElse(null);
        if (existing != null) {
            result.rejectValue("email", null, "There's already an account registered with that email");
            apply.addFlashAttribute("email", users.getEmail() + " already exists");
            model.addAttribute("mess","already exists");
             return "redirect:/admin/adminusers";
        }

        users.setPassword("default");

        String pass = users.getPassword();
        String email = users.getEmail();
        String name = users.getUsername();
            service.addUser(users);
        mailService.sendEmailUsingVelocityTemplate1(email,pass,name);
        apply.addFlashAttribute("success","User registration successful");
        return "redirect:/admin/adminusers";
    }

    @PostMapping("/changePassword")
    public String changePasswordFeature(@ModelAttribute Users users,@AuthenticationPrincipal customUserDetails cust
                                        ){
        String name = cust.getUsername();
        service.updatePass(users,name);
        return "redirect:/profile";
    }


    @GetMapping("/admin/adminusers")
    public String getAllInvoices(
            @RequestParam(value = "message", required = false) String message,
            Model model,Meta meta,@AuthenticationPrincipal customUserDetails cust
    ) {
        List<Users> users= service.getAllUsers().orElse(null);

        String role = cust.getAuthorities().toString();
        //this is to display the cap and credit on the adminusers page
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
        model.addAttribute("role",role);
        model.addAttribute("users", users);
        return "adminusers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id,Model model) {
        Optional<Users> users = service.loadUser(id);
        model.addAttribute("users", users);
        return "editUser";
    }


    @RequestMapping(value="/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(@ModelAttribute("users") Users users,String id) {
        service.updateUsers(users,id);
        return "redirect:/admin/adminusers";
    }

    @RequestMapping(value = "/getOne", produces = "application/json")
    @ResponseBody
    public Optional<Users> getOne(String id){
        log.info("Received request to get user with ID: {}", id);
        return service.getOne(id);
    }

    @RequestMapping(value="/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(String id) {
        service.deleteUser(id);
        return "redirect:/admin/adminusers";
    }

//    @GetMapping("/delete/{id}")
//    public String delete(
//            @PathVariable("id")String id,
//            RedirectAttributes attributes
//
//    ) {
//        service.deleteUser(id);
//        attributes.addAttribute("message", "Invoice with Id : '"+id+"' is removed successfully!");
//        return "redirect:/admin/adminusers";
//    }

    @GetMapping
    public double refresh(Model model){
        double credit = creditLogDAO.refreshCredit();
        model.addAttribute("credit",credit);
        return credit;
    }

    @GetMapping("/changePassword")
    public String ChangePasswordPage(){
        return "password";
    }

    @GetMapping("/Password")
    public String ChangePasswordPage2(){
        return "ChangePassword";
    }

    @PostMapping("/passwordChange")
    public String passwordChange(@RequestParam("newPassword") String newPassword, @AuthenticationPrincipal customUserDetails appl,
                                 @RequestParam("newUsername") String newUsername ){
        String app = appl.getUsername();
        userService.updatePassword(newPassword,app,newUsername);
        return "redirect:/logout";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String submitForgotPasswordForm(@RequestParam("email") String email, Model model) {
        Users user = userDAO.findByEmailIgnoreCase(email).orElse(null);
        if (user == null) {
            model.addAttribute("emailNotFound", true);
            return "redirect:/forgot-password";
        }
        PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetToken(user);
        System.out.println("this is the token " + passwordResetToken.getToken());
        mailService.sendPasswordResetEmail(user,passwordResetToken);
        return "redirect:/forgot-password-confirm";
    }

    @GetMapping("/forgot-password-confirm")
    public String showForgotPasswordConfirmation() {
        return "forgot-password-confirm";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) throws UnsupportedEncodingException {
        if (token.endsWith("%60")) { // Check if token is encoded
            token = URLDecoder.decode(token, "UTF-8").replace("%60", "");
        } else if (token.endsWith("`")) { // Check if token has a trailing backtick
            token = token.substring(0, token.length() - 1);
        }

        Optional<PasswordResetToken> optionalToken = passwordResetTokenService.getPasswordResetToken(token);
        if (optionalToken.isPresent()) {
            PasswordResetToken passwordResetToken = optionalToken.get();
            if (passwordResetToken.isExpired()) {
                return "redirect:/forgot-password";
            }
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            return "redirect:/forgot-password";
        }

    }

    @PostMapping("/reset-password")
    public String submitResetPasswordForm(@RequestParam("token") String token,@RequestParam("password") String password, Model model) {
        PasswordResetToken Token = passwordResetTokenService.getPasswordResetToken(token).orElse(null);
        if (Token == null) {
            return "redirect:/forgot-password";
        }
        if (Token.isExpired()) {
            return "redirect:/forgot-password";
        }
        userService.changePassword(Token.getUser(), password);
        passwordResetTokenService.deletePasswordResetToken(Token);
        return "redirect:/reset-password-success";
    }

    @GetMapping("/reset-password-success")
    public String showResetPasswordSuccess() {
        return "reset-password-success";
    }
}
