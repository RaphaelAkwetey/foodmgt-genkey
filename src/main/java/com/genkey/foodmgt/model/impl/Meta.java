/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.impl;

import com.genkey.foodmgt.model.api.UniqueModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 *
 * @author david
 */
@Entity
@Table(name = "meta", schema="public")
@Getter
@Setter
//@ToString
@RequiredArgsConstructor
//@NoArgsConstructor
public class Meta extends UniqueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @Column
    private double cap;
    @Column
    private double credit;

    @Column
    private double currentFoodPrice;

    public double getCurrentFoodPrice() {
        return currentFoodPrice;
    }

    public void setCurrentFoodPrice(double currentFoodPrice) {
        this.currentFoodPrice = currentFoodPrice;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Meta meta = (Meta) o;
        return getId() != null && Objects.equals(getId(), meta.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
