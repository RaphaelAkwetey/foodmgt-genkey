/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.Config.beans;

import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Vendor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import com.genkey.foodmgt.services.api.VendorService;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author david
 */
/*@Data
@Named
@Scope("session")

public class VendorBean implements Serializable {

    @Inject
    VendorService vendorService;

    private String id;
    private Date updated;
    private boolean active;
    private String updatedBy;
    private boolean deleted;
    private Date deletedDate;
    private String deletedBy;
    private String deletedReason;
    private String name;
    private List<Vendor> names;
    private String account_no;
    private String email_addr;
    private Set<Menu> food;
    private Map<String, Object> parameters;
    private int startRange;
    private int endRange;
    private Vendor activeVendor;
    private boolean isNull=true;
//    private String activeVendorId;

//     public VendorBean() {
//        id = 0;
//    }
    public void create() {
        Vendor vendor = new Vendor();
        vendor.setActive(false);
        vendor.setDeleted(false);
        vendor.setName(getName());
        vendor.setAccount_no(getAccount_no());
        vendor.setEmail_addr(getEmail_addr());
        vendorService.create(vendor);
    }

    public int count() {
        return vendorService.count();
    }

    public void destroy(String id) {
        Vendor vendor = findById(id);
        vendor.setDeleted(true);
        vendorService.destroy(vendor);
    }

    public Vendor findByParam() {
        return vendorService.find(getParameters());
    }

//    pass edited information within function
  /*  public boolean edit(String id) {
        Vendor vendor = findById(id);
        vendor.setUpdated(new Date());
        if (vendor.isActive()) {
            vendor.setActive(isActive());
        }
        if (vendor.isDeleted()) {
            vendor.setDeleted(isDeleted());
        }
        if (getName() != null) {
            vendor.setName(getName());
        }
        if (vendor.getAccount_no() != null) {
            vendor.setAccount_no(getAccount_no());
        }
        if (vendor.getEmail_addr() != null) {
            vendor.setEmail_addr(getEmail_addr());
        }

        if (vendorService.edit(vendor) != null) {
            return true;
        }
        return false;
    }*/

//    specify the id as a parameter so that identification can be made for multiple objects 
 /*   public Vendor findById(String id) {
        return vendorService.find(id);
    }

    public List findAll() {
        return vendorService.findAll();
    }

    public List<Vendor> findRange() {
        return vendorService.findRange(getStartRange(), getEndRange());
    }

    public Vendor getActiveVendor() {
        Vendor result = vendorService.getActiveVendor();
        if (result != null) {
            setActiveVendor(result);
//        reset();
//        return "adminmenu";
        }
        return result;
    }

    public List getActiveVendorMenu() {
        List menu = vendorService.getActiveVendorMenu();
        if (menu!=null){
        isNull = false;
        }
        return menu;
    }

    public String editActive(Vendor vendor, boolean newMode) {
//        Vendor vendor = findById(id);
        vendor.setUpdated(new Date());
        vendor.setActive(newMode);
        vendorService.edit(vendor);
        return "adminvendors.xhtml";
    }

    public void reset() {
        id = "";
        updated = null;
        updatedBy = null;
        deletedDate = null;
        deletedBy = null;
        deletedReason = null;
        name = null;
        account_no = null;
        email_addr = null;
        food = null;
        parameters = null;
        startRange = 0;
        endRange = 0;
    }
}*/
