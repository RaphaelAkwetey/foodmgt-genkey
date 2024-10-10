/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.Vendor;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author david
 */
public interface VendorService  {


    public List<Vendor> getAllVendors();

    public Optional<Vendor> getVendorById(String id);

    public Vendor addVendor(Vendor vendor);

    public Vendor updateVendor(Vendor vendor,String id);

    public void deleteVendor(String id);

    Optional<Vendor> getOneVendor(String id);



    //Vendor getActiveVendor();

    // List<Vendor> getActiveVendorMenu();

}
