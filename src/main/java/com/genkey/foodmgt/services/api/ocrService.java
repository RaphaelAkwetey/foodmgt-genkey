package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.ocrDatabase;

public interface ocrService {

    ocrDatabase updateOCR(ocrDatabase ocrDatabase, String id);
}
