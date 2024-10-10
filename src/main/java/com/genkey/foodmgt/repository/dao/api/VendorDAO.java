/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.Users;
import com.genkey.foodmgt.model.impl.Vendor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author david
 */
@Repository
public interface VendorDAO extends CrudRepository<Vendor, String> {

    //    Vendor getActiveVendor(Class type);
    @Query(value = "select * from vendor where  active = true", nativeQuery = true)
    Vendor getActiveVendor();

    @Query(value = "SELECT m.id, m.food, m.cost FROM Menu m WHERE m.vendor_id = :id", nativeQuery = true)
    List<Vendor> getActiveVendorMenu(int id);

//    List<Vendor> getActiveVendorMenu(String id);4


}
