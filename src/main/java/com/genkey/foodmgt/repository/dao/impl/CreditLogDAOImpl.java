/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.impl;

import com.genkey.foodmgt.model.impl.Users;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import com.genkey.foodmgt.repository.dao.api.CreditLogDAO;
import javax.inject.Named;

/**
 *
 * @author david
 */
@Named("creditLogDAO")
@Log4j2
public class CreditLogDAOImpl extends GenericDAOImpl implements CreditLogDAO{

    public int refreshCredit() {
        int result = -1;
        double credit = retrieveMaxCredit();
        EntityManager em = getEntityManager();
        String hql = "UPDATE CreditLog set credit=:CREDIT";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("CREDIT", credit);
            return result = query.executeUpdate();
        } catch (Exception ex) {
            log.error("Exception thrown in retrieveMaxCredit :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public double retrieveMaxCredit() {
        double result = -1;
        EntityManager em = getEntityManager();
        String hql = "SELECT credit from Meta";
        try {
            Query query = em.createQuery(hql);
            return result = (double) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in retrieveMaxCredit :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public double retrieveInternCredit(Users user) {
        double result = -1;
        String user_id = user.getId();
        EntityManager em = getEntityManager();
        String hql = "SELECT Credit from CreditLog WHERE user_id = :ID";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("ID", user_id);
            result = (double) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in retrieveInternCredit :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}
