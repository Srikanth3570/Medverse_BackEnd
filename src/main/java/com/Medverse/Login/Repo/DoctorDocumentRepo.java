package com.Medverse.Login.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Medverse.Login.Entity.DoctorDocument;

public interface DoctorDocumentRepo extends JpaRepository<DoctorDocument, Long> {
    
	
    List<DoctorDocument> findByPatientUserId(Long userId);
	List<DoctorDocument> findByPatientUserIdAndDoctorDoctorId(Long userId, Long doctorId); 
 // Fetch documents by Doctor ID
}

