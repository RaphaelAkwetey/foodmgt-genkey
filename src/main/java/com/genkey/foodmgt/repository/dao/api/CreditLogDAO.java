/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Users;
import java.io.Serializable;

/**
 *
 * @author david
 */
public interface CreditLogDAO extends GenericDAO, Serializable {

    int refreshCredit();

    double retrieveMaxCredit();
    
    double retrieveInternCredit(Users user);
}
