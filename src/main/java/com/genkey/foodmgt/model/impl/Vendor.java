/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.impl;

import com.genkey.foodmgt.model.api.UniqueModel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author david
 */
@Entity
@Table(name = "vendor", schema="public")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@NoArgsConstructor
public class Vendor extends UniqueModel {

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;
    @Column(nullable = false)
    private String account_no;
    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String email_addr;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    @ToString.Exclude
    private Set<Menu> menus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vendor vendor = (Vendor) o;
        return getId() != null && Objects.equals(getId(), vendor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
