package com.genkey.foodmgt.dto;

import com.genkey.foodmgt.model.impl.Status;

import java.util.Date;

public class SpecificUserOrders {

    private String id;
    private String food;

    private double cost;

    private Date created;

    private Status status;

    private String transactionShorterDate;

    public SpecificUserOrders(String food, double cost, Status status, String transactionShorterDate, String id) {
        this.food = food;
        this.cost = cost;
        this.status = status;
        this.transactionShorterDate = transactionShorterDate;
        this.id = id;
    }

    public SpecificUserOrders(String food, double cost, Date created, String transactionShorterDate) {
        this.food = food;
        this.cost = cost;
        this.created = created;
        this.transactionShorterDate = transactionShorterDate;
    }

    public SpecificUserOrders(String food, double cost, Date created) {
        this.food = food;
        this.cost = cost;
        this.created = created;
    }

    public SpecificUserOrders(String food, double cost, Status status) {
        this.food = food;
        this.cost = cost;
        this.status = status;
    }

    public SpecificUserOrders() {
    }

    public String getTransactionShorterDate() {
        return transactionShorterDate;
    }

    public void setTransactionShorterDate(String transactionShorterDate) {
        this.transactionShorterDate = transactionShorterDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SpecificUserOrders{" +
                "food='" + food + '\'' +
                ", cost=" + cost +
                ", created=" + created +
                '}';
    }
}
