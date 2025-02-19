package com.Medverse.Login.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Medverse.Login.Entity.PatientDocument;

public interface PatientDocumentRepo extends JpaRepository<PatientDocument, Long> {
    
// Correct reference to `userId`

	List<PatientDocument> findByPatient_UserId(Long userId);
}

