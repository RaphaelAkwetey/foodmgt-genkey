/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.impl;

import com.genkey.foodmgt.model.api.UniqueModel;
import com.genkey.foodmgt.util.Days;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author david
 */
@Entity
@Table(name = "menu", schema="public")
@Getter
@Setter
//causes spring security to run in an infinite loop.
//@ToString
//@RequiredArgsConstructor
//@NoArgsConstructor
public class Menu extends UniqueModel {

    @Column(columnDefinition = "VARCHAR(100)")
    private String food;

    private int menuItemPosition;
    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private LocalDate uploadDate;
//    @Enumerated(EnumType.STRING)
//    private Status status;

    //take this out. There's already an inherited isActive
//   @Column(columnDefinition = "BOOLEAN")
//   private boolean isActive;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menuitem")
    @ToString.Exclude
    @Column(nullable = true)
    private Set<Food_Order> orders;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Days_id")
    private WeekDays weekDays;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Vendor_id")
    private Vendor vendor;

    private String status;

    private LocalDate ExpiryDate;

    public Menu(String food, int menuItemcount, double cost, int dayId, LocalDate uploadDate, Vendor vendor,String status,LocalDate ExpiryDate) {
        this.food = food;
        this.menuItemPosition = menuItemcount;
        this.cost = cost;
        this.status = status;
        String day = Days.values()[dayId].toString();
        WeekDays weekDays = new WeekDays(Integer.toString(dayId), day, true);
        this.weekDays = weekDays;
        this.uploadDate = uploadDate;
        this.vendor = vendor;
        this.ExpiryDate=ExpiryDate;
    }

    public Menu(String food, WeekDays weekDays) {
        this.food = food;
        this.weekDays = weekDays;
    }

    public Menu(String food) {
        this.food = food;
    }

    public Menu() {
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getMenuItemPosition() {
        return menuItemPosition;
    }

    public void setMenuItemPosition(int menuItemPosition) {
        this.menuItemPosition = menuItemPosition;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Set<Food_Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Food_Order> orders) {
        this.orders = orders;
    }

    public WeekDays getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(WeekDays weekDays) {
        this.weekDays = weekDays;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

//    public Menu(String food, int cost, Set<Food_Order> orders, Users users) {
//        this.food = food;
//        this.cost = cost;
//        this.orders = orders;
//        this.users = users;
//    }


}
