package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.dto.MetaCapDB;
import com.genkey.foodmgt.model.impl.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDAO extends JpaRepository<Meta, String> {

    @Query(value="SELECT new com.genkey.foodmgt.dto.MetaCapDB(m.cap)" + "FROM Meta m WHERE m.id = '345'")
    MetaCapDB getMetaCap();

    @Query(value="SELECT m.cap FROM Meta m WHERE m.id = '345'")
    double retrieveCap();

    List<Meta> findAll();
}
