/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genkey.foodmgt.model.api;

import com.genkey.foodmgt.model.api.IModel;
import com.genkey.foodmgt.util.IdGenerator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author david
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class UniqueModel implements IModel {

    @Id
    private final String id = IdGenerator.createId();
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final Date created = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    private String createdBy = "SYSTEM";
    private String updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    private String deletedBy;
    private String deletedReason;
    @Column(columnDefinition = "BOOLEAN")
    private boolean active = true;
    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private boolean deleted = false;
    @Version
    private Integer version = 0;



    @PrePersist
    @PreUpdate
    void processDateUpdated() {
        updated = new Date();
    }
}
