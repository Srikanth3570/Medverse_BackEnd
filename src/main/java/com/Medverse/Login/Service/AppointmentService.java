package com.Medverse.Login.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Medverse.Login.Entity.Appointment;
import com.Medverse.Login.Repo.AppointmentRepository;
import com.Medverse.Login.Exception.AppointmentNotFoundException;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository repository;
    
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = repository.findAll();
        System.out.println(appointments);  // Log the appointments to check if they're fetched
        return appointments;
    }
    public Appointment getAppointmentById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id " + id));
    }


    public Appointment createAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
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
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id " + id));
    }

    public void deleteAppointment(Long id) {
        if (!repository.existsById(id)) {
            System.out.println("Appointment with ID " + id + " not found!");
            throw new AppointmentNotFoundException("Appointment not found with id " + id);
        } else {
            System.out.println("Appointment with ID " + id + " found, deleting...");
            repository.deleteById(id);
        }
    }

}
