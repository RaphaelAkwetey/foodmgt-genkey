/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.Config.beans;

import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Vendor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import com.genkey.foodmgt.services.api.MenuService;
import com.genkey.foodmgt.services.api.VendorService;
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
public class MenuBean implements Serializable {

    @Inject
    MenuService menuService;
    VendorService vendorService;

    private String id;
    private Date updated;
    private String updatedBy;
    private Date deletedDate;
    private String deletedBy;
    private String deletedReason;
    private String food;
    private int cost;
    private Set<Food_Order> orders;
    private Vendor vendor;
    private Map<String, Object> parameters;
    private int startRange;
    private int endRange;
    private Menu menu;
    

//    public MenuBean() {
//        id = 0;
//    }
    public boolean create() {
        Menu menu = new Menu();
        menu.setActive(true);
        menu.setDeleted(false);
        menu.setFood(getFood());
        menu.setCost(getCost());
        menu.setOrders(getOrders());
        menu.setVendor(vendorService.getActiveVendor());
        
        return menuService.create(menu)!=null ? true:false;
    }

    public int count() {
        return menuService.count();
    }

    public void destroy() {
        Menu vendor = findById();
        vendor.setDeleted(true);
        menuService.destroy(vendor);
    }

    public Menu findByParam() {
        return menuService.find(getParameters());
    }

    public boolean edit() {
        Menu vendor = findById();
        vendor.setUpdated(new Date());
        if (menuService.edit(getId()) != null) {
            return true;
        }
        return false;
    }

    public Menu findById() {
        return menuService.find(getId());
    }

    public List<Menu> findAll() {
        return menuService.findAll();
    }

    public List<Menu> findRange() {
        return menuService.findRange(getStartRange(), getEndRange());
    }

    public List retrieveMenu(Vendor vendor) {
        return menuService.retrieveMenu(vendor);
    }
}
*/