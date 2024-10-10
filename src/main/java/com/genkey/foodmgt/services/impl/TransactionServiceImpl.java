/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.*;
import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import com.genkey.foodmgt.repository.dao.impl.TransactionDAOImpl;
import com.genkey.foodmgt.services.api.TransactionService;
import com.genkey.foodmgt.util.Days;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author david
 */
//@Named("transactionService")
@Service
@Slf4j
public class TransactionServiceImpl extends BasicServiceImpl<Food_Order> implements TransactionService {

    @Inject
    @Named("transactionDAO")
    private TransactionDAOImpl transactionDao;

    @Autowired
    MenuDAO menuRepository;

    public boolean insertInternTransaction(Food_Order transaction, CreditLog credit) {
        return transactionDao.insertInternTransaction(transaction, credit);
    }

    public boolean insertTransaction(Food_Order transaction) {
        return transactionDao.insertTransaction(transaction);
    }

    public boolean updateTransactionToComplete(Food_Order transaction) {
        return transactionDao.updateTransactionToComplete(transaction);
    }

    public List retrieveAllTransactionsForSpecificPeriod(Date start, Date end) {
        return transactionDao.retrieveAllTransactionsForSpecificPeriod(start, end);
    }

    public List retrieveUserTransactionsForSpecificPeriod(Date start, Date end, Users user) {
        return retrieveUserTransactionsForSpecificPeriod(start, end, user);
    }

    public double retrieveAllTransactionsCostSumForSpecificPeriod(Date start, Date end) {
        return transactionDao.retrieveAllTransactionsCostSumForSpecificPeriod(start, end);
    }

    public double retrieveUserTransactionsCostSumForSpecificPeriod(Date start, Date end, Users user) {
        return retrieveUserTransactionsCostSumForSpecificPeriod(start, end, user);
    }

    public Food_Order parseFoodOrder(int dayPosition, String orderPosition, Users user, Date deliveryDate) {
        try {
            String deliveryDay = Days.values()[dayPosition].toString();
            Meta foodCostDetail = new Meta();
            double cost = foodCostDetail.getCurrentFoodPrice();

            Menu menuItem = menuRepository.getMenuItem(dayPosition, orderPosition)
                    .orElseGet(() -> {
                        Optional<Menu> fallbackMenuItem = menuRepository.getMenuItemFallback(dayPosition, orderPosition);
                        if (fallbackMenuItem.isPresent()) {
                            log.info("Menu id retrieved using the fallback query");
                            return fallbackMenuItem.get();
                        } else {
                            //mailService.sendEmailForFoodNotBeingOrdered("salifuyakubu333@gmail.com", dayPosition, orderPosition, user);
                            throw new NullPointerException("Menu item not found for dayPosition: " + dayPosition + " and orderPosition: " + orderPosition);
                        }
                    });

            log.info("menuItem id for Order is: " + menuItem.getId());
            return new Food_Order(Status.PENDING, deliveryDay, deliveryDate, user, menuItem);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("Invalid day position: " + dayPosition);
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("An error occurred while parsing the food order.");
            e.printStackTrace();
        }
        return null;
    }
}
