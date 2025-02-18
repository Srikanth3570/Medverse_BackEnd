package com.Medverse.Login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.Appointment;
import com.Medverse.Login.Entity.DoctorEntity;
import com.Medverse.Login.Entity.PatientEntity;
import com.Medverse.Login.Repo.DoctorRepo;
import com.Medverse.Login.Repo.RegistrationRepo;
import com.Medverse.Login.Service.AppointmentService;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @Autowired
    private RegistrationRepo patientRepository;
    
    @Autowired
    private DoctorRepo doctorRepository;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        System.out.println("Fetching all appointments...");  // Add logs to track the flow
        List<Appointment> appointments = service.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }


    // Get appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = service.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // Update appointment
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        Appointment appointment = service.updateAppointment(id, updatedAppointment);
        return ResponseEntity.ok(appointment);
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }


    // Create new appointment (POST method)
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        // Ensure patient exists before creating the appointment
        Long userId = appointment.getUserId().getUserId();  // Fetch the patient ID
        PatientEntity patient = patientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID " + userId));
        
        // Set the full patient entity on the appointment
        appointment.setUserId(patient);

        // Ensure doctor exists before creating the appointment
        Long doctorId = appointment.getDoctorId().getDoctorId();  // Fetch the doctor ID
        DoctorEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID " + doctorId));
        
        // Set the full doctor entity on the appointment
        appointment.setDoctorId(doctor);

        // Save the new appointment
        Appointment savedAppointment = service.createAppointment(appointment);

        // Return the saved appointment with HTTP status CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }
}
