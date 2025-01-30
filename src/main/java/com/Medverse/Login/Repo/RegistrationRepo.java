
package com.Medverse.Login.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medverse.Login.Entity.Appointment;
import com.Medverse.Login.Entity.PatientEntity;

@Repository
public interface RegistrationRepo extends JpaRepository<PatientEntity, Long> {  // Use Integer as the type for the ID

    Optional<PatientEntity> findByEmail(String email);  // Find user by email (case-sensitive by default)
}

