package com.Medverse.Login.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Medverse.Login.Entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUserId(Long userId);
}







