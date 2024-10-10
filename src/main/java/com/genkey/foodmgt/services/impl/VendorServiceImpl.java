/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.Vendor;
import com.genkey.foodmgt.repository.dao.api.VendorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.genkey.foodmgt.services.api.VendorService;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author david
 */
@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorDAO vendorRepository;

    @Override
    public List<Vendor> getAllVendors() {
        return (List<Vendor>) vendorRepository.findAll();
    }

    @Override
    public Optional<Vendor> getVendorById(String id) {
        return vendorRepository.findById(id);
    }

    @Override
    public Vendor addVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor updateVendor(Vendor vendor,String id) {
        Vendor existingVendor = vendorRepository.findById(id).orElse(null);
        assert existingVendor != null;
        existingVendor.setEmail_addr(vendor.getEmail_addr());
        existingVendor.setAccount_no(vendor.getAccount_no());
        existingVendor.setName(vendor.getName());
        vendorRepository.save(existingVendor);
        return existingVendor;
    }

    @Override
    public void deleteVendor(String id) {
        vendorRepository.deleteById(id);
    }

    public Optional<Vendor> getOneVendor(String id){
        return vendorRepository.findById(id);
    }

}

