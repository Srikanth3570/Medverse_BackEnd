package com.Medverse.Login.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.DoctorEntity;
import com.Medverse.Login.Exception.DoctorNotFoundException;
import com.Medverse.Login.Service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;

    public DoctorController(DoctorService doctorService, PasswordEncoder passwordEncoder) {
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDoctor(@Valid @RequestBody DoctorEntity doctor, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + result.getAllErrors());
        }
        DoctorEntity savedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.status(201).body(savedDoctor);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorEntity>> getAllDoctors() {
        List<DoctorEntity> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        try {
            DoctorEntity doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (DoctorNotFoundException ex) {
            return ResponseEntity.status(404).body("Doctor not found with ID: " + id);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorEntity updatedDoctor, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + result.getAllErrors());
        }
        try {
            DoctorEntity doctor = doctorService.updateDoctor(id, updatedDoctor);
            return ResponseEntity.ok(doctor);
        } catch (DoctorNotFoundException ex) {
            return ResponseEntity.status(404).body("Doctor not found with ID: " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody DoctorEntity doctor) {
        // Log input data
        System.out.println("Attempting login for email: " + doctor.getEmail());

        // Check if doctor exists using Optional
        Optional<DoctorEntity> foundDoctorOpt = doctorService.getDoctorByEmail(doctor.getEmail());
        if (foundDoctorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

        DoctorEntity foundDoctor = foundDoctorOpt.get();

        // Verify password
        if (passwordEncoder.matches(doctor.getPassword(), foundDoctor.getPassword())) {
            return ResponseEntity.ok(Map.of("status", "success", "data", foundDoctor));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }
}
