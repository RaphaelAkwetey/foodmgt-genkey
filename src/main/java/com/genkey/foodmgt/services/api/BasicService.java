/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jonathan Amos-Asiedu
 * @param <T> The underlying Object Model of the Service class that implements this interface
 */
public interface BasicService<T> extends Serializable {

    int count();
    
    <T> T create(T mediaContentType);

    void destroy(T mediaContentType);

    <T> T edit(T item,String id);

    T find(String id);

    T find(Map<String, Object> parameters);
    
    List<T> findByObject(Map<String, Object> parameters);

    List<T> findAll();

//    List<T> findAll(Map<String, Object> parameters);

    List<T> findRange(int firstResult, int maxResults);
}