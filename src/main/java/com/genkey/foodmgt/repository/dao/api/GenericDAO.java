/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jonathan Amos-Asiedu
 */
public interface GenericDAO extends Serializable{

    Long count(Class type);

    <T> T create(T t);

    <T> T find(Class type, String id);

    List findAll(Class type);

    Object findItemByParameters(Class type, Map<String, Object> parameters);

    List findItemsByParameters(Class type, Map<String, Object> parameters);

    List findRange(Class type, int firstResult, int maxResults);

    void remove(Object t);

    <T> T update(T item);    
}
