package com.genkey.foodmgt.model.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class WeekDays {

    @Id
    private String id;

    private String days;

    private boolean active = true;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Menu> menu;


    public WeekDays(String id, String days, boolean active) {
        this.id = id;
        this.days = days;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WeekDays weekDays = (WeekDays) o;
        return id != null && Objects.equals(id, weekDays.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
