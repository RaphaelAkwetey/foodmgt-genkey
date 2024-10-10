/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.CreditLog;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.repository.dao.impl.CreditLogDAOImpl;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author david
 */
@Named("creditLogService")
public class CreditlogServiceImpl extends BasicServiceImpl<CreditLog> {
    
    @Inject
    @Named("creditLogDAO")
    private CreditLogDAOImpl creditLogDao;

    public int refreshCredit() {
        return creditLogDao.refreshCredit();
    }

    public double retrieveMaxCredit() {
        return creditLogDao.retrieveMaxCredit();
    }
    
    public double retrieveInternCredit(Users user){
    return retrieveInternCredit(user);
    }
}
