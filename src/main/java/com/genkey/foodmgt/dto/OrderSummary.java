package com.genkey.foodmgt.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderSummary {

    private String firstname;
    private String food;

    private Long menuItemPosition;

    private double cost;

    private Date deliveryDate;

    public OrderSummary(String food, Long menuItemPosition, Date deliveryDate) {
        this.food = food;
        this.menuItemPosition = menuItemPosition;
        this.deliveryDate = deliveryDate;
    }

    public OrderSummary(String food, Long menuItemPosition) {
        this.food = food;
        this.menuItemPosition = menuItemPosition;
    }

    public OrderSummary(String food, double cost, Long menuItemPosition) {
        this.food = food;
        this.menuItemPosition = menuItemPosition;
        this.cost = cost;
    }



    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Long getMenuItemPosition() {
        return menuItemPosition;
    }

    public void setMenuItemPosition(Long menuItemPosition) {
        this.menuItemPosition = menuItemPosition;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
