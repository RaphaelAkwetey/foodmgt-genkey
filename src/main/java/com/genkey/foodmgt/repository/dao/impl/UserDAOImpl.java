/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.impl;

import com.genkey.foodmgt.repository.dao.api.UserDAO;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author david
 */
//@Named("userDAO")
//@Log4j2
/*public class UserDAOImpl extends GenericDAOImpl implements UserDAO{

    @Override
    public String getIdByUsername(String username) {
        String result = null;
         EntityManager em = getEntityManager();
        String hql = "SELECT id from Users where username = :username";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("username", username);
            result = (String) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in getIdByUsername :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}*/
