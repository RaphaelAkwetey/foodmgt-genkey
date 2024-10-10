package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.Menu;
import com.genkey.foodmgt.model.impl.ocrDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface ocrDAO extends JpaRepository<ocrDatabase, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from ocr o where o.upload_date = cast(:date as date)",nativeQuery = true)
    void deleteAllByUploadDate(LocalDate date);

}
