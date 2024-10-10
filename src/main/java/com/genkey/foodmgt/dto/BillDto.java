package com.genkey.foodmgt.dto;

import com.genkey.foodmgt.model.impl.*;
import lombok.Data;

import java.util.Date;

@Data
public class BillDto extends Food_Order {

    private String username;

    private double cost;

    private UserRoles userRoles;

    public BillDto(String username, double cost) {
        this.username = username;
        this.cost = cost;
    }

    public BillDto(Users user, String username, double cost, UserRoles userRoles) {
        super(user);
        this.username = username;
        this.cost = cost;
        this.userRoles = userRoles;
    }


    public BillDto(String username, double cost, UserRoles userRoles) {
        this.username = username;
        this.cost = cost;
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
