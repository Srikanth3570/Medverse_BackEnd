package com.Medverse.Login.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Medverse.Login.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}

