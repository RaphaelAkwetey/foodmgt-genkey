package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.ocrDatabase;
import com.genkey.foodmgt.repository.dao.api.ocrDAO;
import com.genkey.foodmgt.services.api.ocrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class ocrServiceImpl implements ocrService {
    @Autowired
    ocrDAO ocrDAO;

    @Override
    public ocrDatabase updateOCR(ocrDatabase ocrDatabase, String id) {
        LocalDate date = LocalDate.now();
        ocrDatabase db = ocrDAO.findById("212").orElse(null);
        db.setId(ocrDatabase.getText());
        db.setUploadDate(date);
        return ocrDAO.save(db);
    }
}
