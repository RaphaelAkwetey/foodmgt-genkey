/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.Meta;
import com.genkey.foodmgt.repository.dao.api.MetaDAO;
import com.genkey.foodmgt.services.api.MetaService;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author david
 */
//@Service("metaService")
@Named("metaService")
public class MetaServiceImpl extends BasicServiceImpl<Meta> implements MetaService {

    @Autowired
    MetaDAO metaDAO;


    @Override
    public Meta CreateMeta(Meta meta) {
        return metaDAO.save(meta);
    }

    @Override
    public Meta UpdateCap(Meta meta, String id) {
        Meta meta2 = metaDAO.findById("345").orElse(null);
        meta2.setCap(meta.getCap());
        return metaDAO.save(meta2);
    }

    @Override
    public Meta UpdatePrice(Meta meta, String id) {
        Meta meta3 = metaDAO.findById("345").orElse(null);
        meta3.setCurrentFoodPrice(meta.getCurrentFoodPrice());
        return metaDAO.save(meta3);
    }


    @Override
    public Meta UpdateCredit(Meta meta, String id) {
        Meta meta1 = metaDAO.findById("345").orElse(null);
        meta1.setCredit(meta.getCredit());
        return metaDAO.save(meta1);
    }
}
