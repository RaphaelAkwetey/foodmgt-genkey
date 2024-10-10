/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.CreditLog;
import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Users;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author david
 */
public interface TransactionService extends BasicService<Food_Order> {

    boolean insertInternTransaction(Food_Order transaction, CreditLog credit);
    
    boolean insertTransaction(Food_Order transaction);

    boolean updateTransactionToComplete(Food_Order transaction);

    List retrieveAllTransactionsForSpecificPeriod(Date start, Date end);

    List retrieveUserTransactionsForSpecificPeriod(Date start, Date end, Users user);

    double retrieveAllTransactionsCostSumForSpecificPeriod(Date start, Date end);

    double retrieveUserTransactionsCostSumForSpecificPeriod(Date start, Date end, Users user);

    Food_Order parseFoodOrder(int dayPosition, String orderPosition, Users user, Date deliveryDate);

}
