/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.impl;

/**
 *
 * @author david
 */
/*@Named("vendorDAO")
@Log4j2
public class VendorDAOImpl  implements  {

    @Override
    public Vendor getActiveVendor(Class type) {
        Vendor result = null;
        EntityManager em = getEntityManager();
        String hql = "SELECT v FROM " + type.getSimpleName()+" v WHERE v.active = true";
        try {
            Query query = em.createQuery(hql,type);
            result = (Vendor) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in getActiveVendor :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

    @Override
    public List<Vendor> getActiveVendorMenu(String vendor_id){
     List result = null;
        EntityManager em = getEntityManager();
        String hql = "SELECT id, food, cost, isActive FROM Menu WHERE vendor_id = :ID";
        try {
            Query query = em.createQuery(hql);
            query.setParameter("ID", vendor_id);
            result = query.getResultList();
        } catch (Exception ex) {
            log.error("Exception thrown in getActiveVendorMenu :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }

}*/
