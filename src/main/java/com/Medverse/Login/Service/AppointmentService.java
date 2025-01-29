package com.Medverse.Login.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medverse.Login.Entity.Appointment;
import com.Medverse.Login.Repo.AppointmentRepository;

@Service

public class AppointmentService {


    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Integer id) {
        return repository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public Appointment updateAppointment(Integer id, Appointment updatedAppointment) {
        return repository.findById(id)
                .map(existingAppointment -> {
                    existingAppointment.setUserId(updatedAppointment.getUserId());
                    existingAppointment.setDoctorId(updatedAppointment.getDoctorId());
                    existingAppointment.setHospitalId(updatedAppointment.getHospitalId());
                    existingAppointment.setAppointmentDateTime(updatedAppointment.getAppointmentDateTime());
                    existingAppointment.setStatus(updatedAppointment.getStatus());
                    existingAppointment.setReasonForVisit(updatedAppointment.getReasonForVisit());
                    existingAppointment.setNotes(updatedAppointment.getNotes());
                    return repository.save(existingAppointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
    }

    public void deleteAppointment(Integer id) {
        repository.deleteById(id);
    }
}
