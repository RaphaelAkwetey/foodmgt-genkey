package com.genkey.foodmgt.services.impl;

import com.genkey.foodmgt.model.impl.FileModel;
import com.genkey.foodmgt.repository.dao.api.FileDAO;
import com.genkey.foodmgt.services.api.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    FileDAO fileDAO;

    @Override
    public FileModel findByName(String name) {
        return fileDAO.findByName(name);
    }

    @Override
    public FileModel findById(Long id) {
        return fileDAO.findById(id).orElse(null);
    }
}
