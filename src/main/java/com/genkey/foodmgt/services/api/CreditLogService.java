/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.CreditLog;
import com.genkey.foodmgt.model.impl.Users;

/**
 *
 * @author david
 */
public interface CreditLogService extends BasicService<CreditLog> {
    
    int refreshCredit();
    
    double retrieveMaxCredit();
    
    double retrieveInternCredit(Users user);
}
