package com.genkey.foodmgt.dto;

import lombok.Data;

@Data
public class FoodOrderDto {
    private String firstname;
    private String food;
    private double cost;

    public FoodOrderDto(String firstname, String food, double cost) {
        this.firstname = firstname;
        this.food = food;
        this.cost = cost;
    }

    public FoodOrderDto() {
    }
}
