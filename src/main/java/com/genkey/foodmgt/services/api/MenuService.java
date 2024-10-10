/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.dto.OrderResponse;
import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.Vendor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @author david
 */
@Service
public interface MenuService {

    Food_Order processOrds(String id);

    Food_Order processOrdss(String id);

    Food_Order updateOrder(String id,String username,String food);

    public Optional<Food_Order> getOrderById(String id);
    public List<Menu> getAllMenu();

    List<OrderResponse> getAllOrders();

    public Optional<Menu> getMenuById(String id);

    public Menu addMenu(Menu menu);

    public Menu updateMenu(Menu menu, String id);

    public void deleteOrder(String id);

    void processOrders(List<OrderResponse> menu);

    List<Menu> getOrdersByRange(final Date startDate, final Date endDate);

    public List<Menu> findBySearch(Date start, Date end);
    // List retrieveMenu(Vendor vendor);

    public boolean persistMenuMap(LinkedHashMap<String, List<String>> map);
}
