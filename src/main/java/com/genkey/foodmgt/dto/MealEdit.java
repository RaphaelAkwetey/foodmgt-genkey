package com.genkey.foodmgt.dto;

import lombok.Data;

@Data
public class MealEdit {

    private String id;

    private String days;

    private String food;

    private String status;


    public MealEdit(String id, String days, String food, String status) {
        this.id = id;
        this.days = days;
        this.food = food;
        this.status = status;
    }

    public MealEdit(String days, String food) {
        this.days = days;
        this.food = food;
    }
}
