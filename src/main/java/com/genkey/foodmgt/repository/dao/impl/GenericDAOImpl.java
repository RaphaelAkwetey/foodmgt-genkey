/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.impl;

import com.genkey.foodmgt.repository.dao.api.GenericDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

/**
 *
 * @author Jonathan Amos-Asiedu
 */
@Named("genericDAO")
@Log4j2
public class GenericDAOImpl implements GenericDAO {

//@PersistenceContext(unitName="genericDAO", type=PersistenceContextType.EXTENDED)    
    private EntityManagerFactory emf;
//    @Autowired
//    SessionFactory sessionFactory;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    @Override
    public <T> T create(T t) {
        EntityManager em = this.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Exception thrown in create :", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return t;
    }

    @Override
    public <T> T update(T t) {
        EntityManager em = this.emf.createEntityManager();
        T tt = null;
        try {
            em.getTransaction().begin();
         tt = em.merge(t);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Exception thrown in update :", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return tt;
    }

    @Override
    public void remove(Object t) {
        EntityManager em = this.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            t = em.merge(t);
            em.remove(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            log.error("Exception thrown in remove :", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Long count(Class type) {
        EntityManager em = this.emf.createEntityManager();
        Long count = 0L;
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM " + type.getSimpleName() + " e", Long.class);
            count = (Long) query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in count :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return count;
    }

    @Override
    public List findAll(Class type) {
        EntityManager em = this.emf.createEntityManager();
        List objects = null;
        try {
            Query query = genericQueryBuilder(em, type, null);
            objects = new ArrayList(query.getResultList());
        } catch (Exception ex) {
            log.error("Exception thrown in findAll :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return objects;
    }

    @Override
    public <T> T find(Class type, String id) {
//        HashMap parameters = new HashMap<String, Object>();
//        parameters.put("id", id);
//        return findItemByParameters(type, parameters);

        EntityManager em = this.emf.createEntityManager();
//        Session session = em.unwrap(Session.class);
        T tt = null;
        try {
//            String typeoftype = type.getCanonicalName();
            tt = (T) em.find(type, id, LockModeType.NONE);
        } catch (Exception ex) {
            log.error("Exception thrown in find for id : " + id, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return tt;
    }

    @Override
    public List findRange(Class type, int firstResult, int maxResults) {
        EntityManager em = this.emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        List entries = null;
        try {
            Query query = genericQueryBuilder(session, type, null).setFirstResult(firstResult).setMaxResults(maxResults);
            entries = new ArrayList(query.getResultList());
        } catch (Exception ex) {
            log.error("Exception thrown in findRange for type >" + type.toString() + "< for firstResult >" + firstResult + "< and maxResult >" + maxResults, ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return entries;
    }

    @Override
    public Object findItemByParameters(Class type, Map<String, Object> parameters) {

        EntityManager em = this.emf.createEntityManager();
//        Session session = em.unwrap(Session.class);
        Object tt = null;
        try {
            Query query = genericQueryBuilder(em, type, parameters);
            tt = query.getSingleResult();
        } catch (Exception ex) {
            log.error("Exception thrown in findItemByParameters", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return tt;
    }

    @Override
    public List findItemsByParameters(Class type, Map<String, Object> parameters) {

        EntityManager em = this.emf.createEntityManager();
//        Session session = em.unwrap(Session.class);
        List tt = null;
        try {
            Query query = genericQueryBuilder(em, type, parameters);
            tt = query.getResultList();
        } catch (Exception ex) {
            log.error("Exception thrown in findItemsByParameters :", ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return tt;
    }

    public Query genericQueryBuilder(EntityManager em, Class type, Map<String, Object> parameters) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(type);
        Root root = criteriaQuery.from(type);
        Predicate[] criteriaList = null;

        try {

            Map<String, Object> modifiedParams = new HashMap();
            if (parameters != null && !parameters.isEmpty()) {
                parameters.keySet().stream().filter((key) -> (parameters.get(key) != null)).forEach((key) -> {
                    modifiedParams.put(key, parameters.get(key));
                });

                int count = 0;
                criteriaList = new Predicate[modifiedParams.size()];
                Predicate predicate = null;

                for (String key : modifiedParams.keySet()) {
                    predicate = criteriaBuilder.equal(root.get(key), modifiedParams.get(key));
                    criteriaList[count] = predicate;
                    count++;
                }
            }
            if (criteriaList != null) {
                criteriaQuery.where(criteriaBuilder.and(criteriaList));
            }
        } catch (Exception ex) {
            log.error("Failed to build query", ex);
        }

        TypedQuery query = em.createQuery(criteriaQuery);
        return query;
    }
}
