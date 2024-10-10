/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author david
 */
public interface IModel extends Serializable {
    String getId();
    
    Date getCreated();
    
    String getCreatedBy();

    Date getUpdated();
    
    String getUpdatedBy();

    boolean isActive();

    boolean isDeleted();

//    Integer getVersion();
}