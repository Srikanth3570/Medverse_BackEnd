package com.Medverse.Login.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Medverse.Login.Entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // JpaRepository already provides basic methods like findById(Long id)
}
