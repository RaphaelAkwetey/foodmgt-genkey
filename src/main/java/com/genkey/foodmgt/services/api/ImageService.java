package com.genkey.foodmgt.services.api;

import com.genkey.foodmgt.model.impl.FileModel;

import java.util.Optional;

public interface ImageService {
    FileModel findByName(String name);
    FileModel findById(Long id);
}
