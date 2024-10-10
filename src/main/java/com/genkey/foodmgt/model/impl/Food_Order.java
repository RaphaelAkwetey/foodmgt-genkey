/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.impl;

import com.genkey.foodmgt.model.api.UniqueModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
/**
 *
 * @author David
 */
@Entity
@Table(name = "food_order", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Food_Order extends UniqueModel {
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @Enumerated(EnumType.STRING)
    private Status status;

//    @Column (columnDefinition = "BOOLEAN",nullable = false)
//    private boolean isComplete;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String transactionShorterDate;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private Users user;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "food_id", nullable = true)
    private Menu menuitem;

    public Food_Order(Status status, String transactionShorterDate, Date deliveryDate, Users user, Menu menuItem) {
        this.status = status;
        this.transactionShorterDate = transactionShorterDate;
        this.deliveryDate = deliveryDate;
        this.user = user;
        this.menuitem = menuItem;
    }

    public Food_Order(Status pending, String deliveryDay, Date deliveryDate, Users user, boolean menuItem) {
    }

    public Food_Order(Users user) {
    }


}