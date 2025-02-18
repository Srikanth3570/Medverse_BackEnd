package com.Medverse.Login.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.Medverse.Login.Entity.DoctorEntity;

public interface DoctorRepo extends JpaRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> findByEmail(String email);
}
