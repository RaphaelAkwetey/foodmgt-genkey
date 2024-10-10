 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.api;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author david
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(callSuper=false)
public abstract class Person extends UniqueModel implements IPerson {
    @Column(nullable = false,columnDefinition = "VARCHAR(100)")
    private String firstname;
    @Column(nullable = false,columnDefinition = "VARCHAR(100)")
    private String lastname;
    @Column(nullable = false,columnDefinition = "VARCHAR(100)")
    private String username;
    @Column(nullable = false,columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(nullable = false,columnDefinition = "VARCHAR(100)")
    private String password;
}
