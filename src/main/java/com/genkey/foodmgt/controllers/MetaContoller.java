package com.genkey.foodmgt.controllers;

import com.genkey.foodmgt.dto.MetaCapDB;
import com.genkey.foodmgt.model.impl.Meta;
import com.genkey.foodmgt.repository.dao.api.MetaDAO;
import com.genkey.foodmgt.services.api.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@Slf4j
public class MetaContoller  {



    @Autowired
    MetaDAO metaDAO;

    @Autowired
    MetaService metaService;


    @PostMapping("/editCap")
    public String EditCap(Meta meta,String id){
        try {
            metaService.UpdateCap(meta,id);
            log.info("credit successfully updated");
        } catch(Exception e){
            Meta meta3 = new Meta();
            meta3.setCap(meta.getCap());
            meta3.setId("345");
            metaService.CreateMeta(meta3);
            log.info("create meta Cap functionality is successful");

        }
        return "redirect:/admin/adminusers";
    }


    @PostMapping("/editPrice")
    public String EditPrice(Meta meta,String id){
        try {
            metaService.UpdatePrice(meta,id);
            log.info("Price successfully updated");
        } catch(Exception e){
            Meta meta3 = new Meta();
            meta3.setCurrentFoodPrice(meta.getCurrentFoodPrice());
            meta3.setId("345");
            metaService.CreateMeta(meta3);
            log.info("create meta Price functionality is successful");

        }
        return "redirect:/admin/adminusers";
    }


    @PostMapping("/editCredit")
    public String EditCredit(Meta meta,String id){

        try {
            Meta meta1 = metaDAO.findById("345").orElse(null);
            metaService.UpdateCredit(meta,id);
            log.info("credit successfully updated");
        } catch(Exception e){
            Meta meta3 = new Meta();
            meta3.setCredit(meta.getCredit());
            meta3.setId("345");
            metaService.CreateMeta(meta3);
            log.info("create meta Credit functionality is successful");

        }
        return "redirect:/admin/adminusers";
    }

}
