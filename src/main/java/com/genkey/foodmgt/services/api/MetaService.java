/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.Meta;

/**
 *
 * @author david
 */
public interface MetaService extends BasicService<Meta>{

    Meta CreateMeta(Meta meta);

    Meta UpdateCap(Meta meta,String id);

    Meta UpdatePrice(Meta meta, String id);

    Meta UpdateCredit(Meta meta, String id);
}
