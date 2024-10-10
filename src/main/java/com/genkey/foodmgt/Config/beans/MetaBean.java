/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.Config.beans;

import com.genkey.foodmgt.model.impl.Meta;
import com.genkey.foodmgt.services.api.MetaService;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author david
 */
/*@Data
@Named
@Scope("session")
public class MetaBean implements Serializable {

    @Inject
    MetaService metaService;

//    @PostConstruct
//    private void init() {
//    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        ServletContext servletContext = (ServletContext) externalContext.getContext();
//    WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
//                                   getAutowireCapableBeanFactory().
//                                   autowireBean(this);
//    }
// 
//    private void readObject(ObjectInputStream ois) 
//                            throws ClassNotFoundException, IOException {
//        ois.defaultReadObject();
//        init();
//    }
    private String id;
    private double cap;
    private double credit;

//    public MetaBean() {
//    }
    public String editCap() {
        Meta meta = find();
        meta.setCap(getCap());
        meta = metaService.edit(meta);
//        setCap(meta.getCap());
//        setCredit(meta.getCredit());
        return "adminusers";
    }

    public String editCredit() {
        boolean error = false;
        Meta meta = find();
        if (meta != null) {
            meta.setCredit(getCredit());
            if (metaService.edit(meta) == null) {
                error = true;
            }
        }
        return "adminusers";
    }

    public Meta find() {
        return metaService.find("1");
    }

    public double getPersistentCredit() {
        Meta meta = find();
        return meta.getCredit();
    }

    public double getPersistentCap() {
        Meta meta = find();
        return meta.getCap();
    }
}*/
