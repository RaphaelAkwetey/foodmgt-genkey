/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.impl;

import com.genkey.foodmgt.model.impl.Vendor;
import com.genkey.foodmgt.repository.dao.api.MenuDAO;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author david
 */
/*@Named("menuDAO")
@Log4j2
public class MenuDAOImpl extends GenericDAOImpl implements MenuDAO {

    @Override
    public List retrieveMenu(Vendor vendor) {
        List result = null;
        String vendor_id = vendor.getId();
        String hql = "SELECT Menu.food and Menu.cost FROM Menu WHERE vendor.getId()=:ID";
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery(hql);
            query.setParameter("ID", vendor_id);
            result = new ArrayList(query.getResultList());
        } catch (Exception ex) {
            log.error("Exception thrown in retrieveMenu:", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}*/
