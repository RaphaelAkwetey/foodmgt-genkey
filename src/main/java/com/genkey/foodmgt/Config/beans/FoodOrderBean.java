/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.Config.beans;

import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.services.api.TransactionService;
import com.genkey.foodmgt.util.DateRangeMap;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Calendar.getInstance;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author david
 */
/*@Data
@Named
@Scope("session")
public class FoodOrderBean implements Serializable {

    @Inject
    TransactionService transactionService;
    
    private String id;
    private Date updated;
    private String updatedBy;
    private Date deletedDate;
    private String deletedBy;
    private String deletedReason;
    private Date deliveryDate;
    private boolean isComplete;
    private Users user;
    private Menu menuitem;
    private Map<String, Object> parameters;
    private int startRange;
    private int endRange;
    private Date start;
    private Date end;

//    public FoodOrderBean() {
//        id = 0;
//    }

    public void create() {
        Food_Order transaction = new Food_Order();
        transaction.setActive(true);
        transaction.setDeleted(false);
        transaction.setDeliveryDate(getDeliveryDate());
        transaction.setUser(getUser());
        transaction.setComplete(false);
        transaction.setMenuitem(getMenuitem());
        Date created = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        transaction.setTransactionShorterDate(df.format(created));

        transactionService.create(transaction);

    }

    public int count() {
        return transactionService.count();
    }

    public void destroy() {
        Food_Order transaction = findById();
        transaction.setDeleted(true);
        transactionService.destroy(transaction);
    }

    public Food_Order findByParam() {
        return transactionService.find(getParameters());
    }

    public boolean edit() {
        Food_Order transaction = findById();
        transaction.setUpdated(new Date());
        if (transactionService.edit(getId()) != null) {
            return true;
        }
        return false;
    }

    public Food_Order findById() {
        return transactionService.find(getId());
    }

    public List<Food_Order> findAll() {
        return transactionService.findAll();
    }

    public List<Food_Order> findRange() {
        return transactionService.findRange(getStartRange(), getEndRange());
    }
    public List<Food_Order> findTodaysOrders(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String transactionShorterDate = dateFormat.format(date);
        Map dateObject = new HashMap();
        dateObject.put("transactionShorterDate", transactionShorterDate);
    return transactionService.findByObject(dateObject);
    }

    public boolean insertTransaction() {
        return transactionService.updateTransactionToComplete(findById());
    }

    public boolean updateTransactionToComplete() {
        return transactionService.updateTransactionToComplete(findById());
    }

    public List retrieveAllTransactionsForSpecificPeriod() {
        return transactionService.retrieveAllTransactionsForSpecificPeriod(getStart(), getEnd());
    }

    public List retrieveUserTransactionsForSpecificPeriod() {
        return transactionService.retrieveUserTransactionsForSpecificPeriod(getStart(), getEnd(), getUser());

    }

    public double retrieveAllTransactionsCostSumForSpecificPeriod() {
        return transactionService.retrieveAllTransactionsCostSumForSpecificPeriod(getStart(), getEnd());

    }
    
    public double getThisMonthsTotalCostOfFood(){
    DateRangeMap rangeMap = new DateRangeMap();
    Date date = new Date();
    Calendar cal = getInstance();
    cal.setTime(date);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    Calendar start = rangeMap.getDateRanges().get((month-1)%12);
    setStart(start.getTime());
    Calendar end;
    if (day<24){
    end = cal;
    }else{
    end = rangeMap.getDateRanges().get(month);
    setEnd(end.getTime());
    }
    return retrieveAllTransactionsCostSumForSpecificPeriod();
    }

    public double retrieveUserTransactionsCostSumForSpecificPeriod() {
        return transactionService.retrieveUserTransactionsCostSumForSpecificPeriod(getStart(), getEnd(), getUser());

    }
}*/
