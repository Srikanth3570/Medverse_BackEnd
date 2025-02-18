package com.Medverse.Login.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Medverse.Login.Entity.DoctorEntity;
import com.Medverse.Login.Exception.DoctorNotFoundException;
import com.Medverse.Login.Repo.DoctorRepo;

@Service
public class DoctorService {

    private final DoctorRepo doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepo doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<DoctorEntity> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    // Create a new doctor
    public DoctorEntity saveDoctor(DoctorEntity doctor) {
        // Encode the password before saving the doctor
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    // Get all doctors
    public List<DoctorEntity> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get doctor by ID
    public DoctorEntity getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> 
            new DoctorNotFoundException("Doctor not found with ID: " + id));
    }

    // Update doctor details
    public DoctorEntity updateDoctor(Long id, DoctorEntity updatedDoctor) {
        DoctorEntity existingDoctor = getDoctorById(id);
        existingDoctor.setFirstName(updatedDoctor.getFirstName());
        existingDoctor.setLastName(updatedDoctor.getLastName());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        existingDoctor.setExperienceYears(updatedDoctor.getExperienceYears());
        existingDoctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        existingDoctor.setAvailabilitySchedule(updatedDoctor.getAvailabilitySchedule());
        existingDoctor.setHospitalId(updatedDoctor.getHospitalId());

        // Encode new password if it's provided
        if (updatedDoctor.getPassword() != null && !updatedDoctor.getPassword().isEmpty()) {
            existingDoctor.setPassword(passwordEncoder.encode(updatedDoctor.getPassword()));
        }

        return doctorRepository.save(existingDoctor);
    }

    // Delete a doctor by ID
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
