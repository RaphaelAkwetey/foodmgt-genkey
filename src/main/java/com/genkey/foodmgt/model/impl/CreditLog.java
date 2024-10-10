/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.impl;

import com.genkey.foodmgt.model.api.UniqueModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author david
 */
@Entity
@Table(name = "creditlog", schema="public")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreditLog extends UniqueModel {

    @Column
    private double credit;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
