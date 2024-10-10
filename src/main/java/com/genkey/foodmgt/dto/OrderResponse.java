package com.genkey.foodmgt.dto;

import com.genkey.foodmgt.model.impl.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;


public class OrderResponse {

    private String id;
    private String firstname;
    private String food;
    private double cost;

    private Status status;

    private Date created;
    private Date deliveryDate;

    private String transactionShorterDate;



    public OrderResponse(String firstname, String food, double cost,Status status) {
        this.firstname = firstname;
        this.food = food;
        this.cost = cost;
        this.status=status;
    }

    public OrderResponse(String firstname) {
        this.firstname = firstname;
    }

    public OrderResponse(String firstname, String food, double cost, Date deliveryDate) {
        this.firstname = firstname;
        this.food = food;
        this.cost = cost;
        this.deliveryDate = deliveryDate;
    }

    public OrderResponse(String food, double cost, Date deliveryDate, String transactionShorterDate) {
        this.food = food;
        this.cost = cost;
        this.deliveryDate = deliveryDate;
        this.transactionShorterDate = transactionShorterDate;
    }

    public OrderResponse(String firstname, String food, double cost, Status status, String transactionShorterDate, String id) {
        this.firstname = firstname;
        this.food = food;
        this.cost = cost;
        this.status = status;
        this.transactionShorterDate = transactionShorterDate;
        this.id = id;
    }

    public OrderResponse() {
    }


    public OrderResponse(double cost) {
        this.cost = cost;
    }

    public OrderResponse(String firstname, String food, String transactionShorterDate, double cost, Date created ) {
        this.firstname = firstname;
        this.food = food;
        this.transactionShorterDate = transactionShorterDate;
        this.cost = cost;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "OrderResponse{" +
                "firstname='" + firstname + '\'' +
                ", food='" + food + '\'' +
                ", cost=" + cost +
                '}';
    }
}
