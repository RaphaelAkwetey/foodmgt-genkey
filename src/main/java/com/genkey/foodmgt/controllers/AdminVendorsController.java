package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.Config.customUserDetails;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.Vendor;
import com.genkey.foodmgt.services.api.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping
public class AdminVendorsController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/saveVendor")
    public String saveVendor(
            @ModelAttribute Vendor vendor,
            Model model
    ) {
        vendorService.addVendor(vendor);
        String id = vendorService.addVendor(vendor).getId();
        String message = "Record with id : '"+id+"' is saved successfully !";
        model.addAttribute("message", message);
        return "redirect:/admin/adminvendors";
    }
    @GetMapping("/createVendor")
    public String createVendor(Model model) {
        Vendor vendor = new Vendor();
        model.addAttribute("vendor", vendor);
        return "createVendor";
    }

    @GetMapping("/admin/adminvendors")
    public String getAllVendors(
            @RequestParam(value = "message", required = false) String message,
            Model model,@AuthenticationPrincipal customUserDetails cust
    ) {
        List<Vendor> vendor= vendorService.getAllVendors();
        String role = cust.getAuthorities().toString();
        model.addAttribute("role",role);
        model.addAttribute("vendor", vendor);
        model.addAttribute("message", message);
        return "adminvendors";
    }

    @GetMapping("/editVendor/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Optional<Vendor> vendor = vendorService.getVendorById(id);
        model.addAttribute("vendor", vendor);
        return "editVendor";
    }
    @RequestMapping(value="/updateVendor", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(@ModelAttribute("vendors") Vendor vendor,String id) {
        vendorService.updateVendor(vendor,id);
        return "redirect:/admin/adminvendors";
    }

    @RequestMapping(value="/getOneVendor" ,produces = "application/json")
    @ResponseBody
    public Optional<Vendor> getOneVendor(String id){
        return vendorService.getOneVendor(id);
    }

    @RequestMapping(value="/deleteVendor", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(String id) {
        vendorService.deleteVendor(id);
        return "redirect:/admin/adminvendors";
    }


}