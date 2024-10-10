package com.genkey.foodmgt.dto;

import com.genkey.foodmgt.model.impl.Status;

import java.util.Date;

public class Summary {

    private String id;

    private String firstname;

    private String food;

    private Long menuItemPosition;

    private Date deliveryDate;

    private String transactionShorterDate;


    private Status status;

    public Summary(String id ,String firstname, String food, Date deliveryDate, String transactionShorterDate,Status status) {


        this.firstname = firstname;
        this.food = food;
        this.deliveryDate = deliveryDate;
        this.transactionShorterDate = transactionShorterDate;

        this.status = status;
        this.id = id;

    }

    public Summary(String firstname, Long menuItemPosition) {
        this.firstname = firstname;
        this.menuItemPosition = menuItemPosition;
    }

    public Summary(String firstname, String food) {
        this.firstname = firstname;
        this.food = food;
    }


    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getMenuItemPosition() {
        return menuItemPosition;
    }

    public void setMenuItemPosition(Long menuItemPosition) {
        this.menuItemPosition = menuItemPosition;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTransactionShorterDate() {
        return transactionShorterDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTransactionShorterDate(String transactionShorterDate) {
        this.transactionShorterDate = transactionShorterDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
