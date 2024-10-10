/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.repository.dao.api.GenericDAO;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import com.genkey.foodmgt.services.api.BasicService;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jonathan Amos-Asiedu
 * @param <T> The underlying Object Model that maps to the service class that is
 * expected to extend this class
 *
 */
//@Service("basicService")
@Named("basicService")
public class BasicServiceImpl<T> implements BasicService<T> {

    @Inject
    @Named("genericDAO") 
    private GenericDAO dao;

    @Override
    public <T> T create(T item) {
        return dao.create(item);
    }

    @Override
    public <T> T edit(T item,String id) {
        return dao.update(item);
    }

    @Override
    public void destroy(T item) {
        dao.remove(item);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll(getClazz());
    }

    @Override
    public List<T> findRange(int firstResult, int maxResults) {
        return dao.findRange(getClazz(), firstResult, maxResults);
    }

    @Override
    public T find(String id) {
        return (T) dao.find(getClazz(), id);
    }

//    @Override
//    public List<T> findAll(Map<String, Object> parameters) {
//        return dao.findItemsByParameters(getClazz(), parameters);
//    }
    @Override
    public T find(Map<String, Object> parameters) {
        return (T) dao.findItemByParameters(getClazz(), parameters);
    }
    
     @Override
    public List<T> findByObject(Map<String, Object> parameters) {
        return dao.findItemsByParameters(getClazz(), parameters);
    }

    @Override
    public int count() {
        return dao.count(getClazz()).intValue();
    }

    /**
     * @return the dao
     */
    protected GenericDAO getDao() {
        return dao;
    }

    /**
     * @return the actual class representation of the generic object
     */
    protected Class<T> getClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void startupMessage() {
        System.out.println("\n*******************************************************");
        System.out.println("Populating " + getClazz().getSimpleName());
        System.out.println("*******************************************************\n");
    }

    public void startupMessageComplete() {
        int total = count();
        System.out.println("\n*******************************************************");
        System.out.println("Total " + getClazz().getSimpleName() + " = " + total);
        System.out.println("Populating " + getClazz().getSimpleName() + " Successful");
        System.out.println("*******************************************************\n");
    }
}
