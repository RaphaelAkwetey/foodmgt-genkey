package com.genkey.foodmgt.repository.dao.api;

import com.genkey.foodmgt.model.impl.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface FileDAO extends JpaRepository<FileModel, Long> {
    FileModel findByName(String name);
    Optional<FileModel> findById(Long id);
}

