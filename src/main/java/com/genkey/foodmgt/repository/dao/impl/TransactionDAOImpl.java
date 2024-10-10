/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.impl;

import com.genkey.foodmgt.model.impl.Food_Order;
import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.CreditLog;
import com.genkey.foodmgt.repository.dao.api.TransactionDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author david
 */
@Named("transactionDAO")
@Log4j2
public class TransactionDAOImpl extends GenericDAOImpl implements TransactionDAO {

    public boolean insertInternTransaction(Food_Order transaction, CreditLog creditLog) {
        double cost = transaction.getMenuitem().getCost();
//        double newCredit = -1;
        if (creditLog.getCredit() >= cost) {
            if (create(transaction)!=null){
            return true;
            }
        }
        return false;
    }

    public boolean insertTransaction(Food_Order transaction) {
        if (create(transaction) != null) {
            return true;
        }

        return false;
    }

    public boolean updateInternTransactionToComplete(Food_Order transaction, CreditLog creditLog) {
        int result = -1;
        CreditLogDAOImpl creditLogDao = new CreditLogDAOImpl();

        String transaction_id = transaction.getId();
        EntityManager em = getEntityManager();
        double cost = transaction.getMenuitem().getCost();
        creditLog.setCredit(creditLog.getCredit() - cost);
        creditLogDao.update(creditLog);
        String hql = "UPDATE Food_Order set isComplete = :Complete WHERE id = :ID";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("ID", transaction_id);
            query.setParameter("Complete", true);
            result = query.executeUpdate();
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTransactionToComplete(Food_Order transaction) {
        int result = -1;

        String transaction_id = transaction.getId();
        EntityManager em = getEntityManager();
        String hql = "UPDATE Food_Order set isComplete = :Complete WHERE id = :ID";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("ID", transaction_id);
            query.setParameter("Complete", true);
            result = query.executeUpdate();
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List retrieveAllTransactionsForSpecificPeriod(Date start, Date end) {
        List result = null;
        EntityManager em = getEntityManager();
        String hql = "SELECT food, user, deliveryDate, isComplete FROM Food_Order WHERE Food_Order.created  BETWEEN :sd AND :ed";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("sd", start);
            query.setParameter("ed", end);
            result = new ArrayList(query.getResultList());;
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public List retrieveUserTransactionsForSpecificPeriod(Date start, Date end, Users user) {
        List result = null;
        EntityManager em = getEntityManager();
        String hql = "SELECT Food_Order.food, Food_Order.user, Food_Order.deliveryDate, food.getCost(), Food_Order.isComplete FROM Food_Order WHERE user = :User AND (Food_Order.created  BETWEEN :sd AND :ed)";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("sd", start);
            query.setParameter("ed", end);
            result = new ArrayList(query.getResultList());;
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public double retrieveAllTransactionsCostSumForSpecificPeriod(Date start, Date end) {
        Double result = null;
        EntityManager em = getEntityManager();
        String hql = "SELECT Sum(e.food.cost) FROM Food_Order e WHERE e.created  BETWEEN :sd AND :ed";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("sd", start);
            query.setParameter("ed", end);
            result = (Double) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    public double retrieveUserTransactionsCostSumForSpecificPeriod(Date start, Date end, Users user) {
        Double result = null;
        EntityManager em = getEntityManager();
        String hql = "SELECT Sum(e.food.cost) FROM Food_Order e WHERE e.user = :User AND (e.created  BETWEEN :sd AND :ed)";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("sd", start);
            query.setParameter("ed", end);
            result = (Double) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }


}
