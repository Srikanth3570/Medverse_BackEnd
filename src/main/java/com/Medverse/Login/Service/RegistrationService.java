package com.Medverse.Login.Service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Medverse.Login.Entity.PatientEntity;
import com.Medverse.Login.Repo.RegistrationRepo;

@Service
public class RegistrationService {

    private final RegistrationRepo registrationRepo;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(RegistrationRepo registrationRepo, PasswordEncoder passwordEncoder) {
        this.registrationRepo = registrationRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Save the RegistrationEntity without encoding the password
    public PatientEntity save(PatientEntity user) {
        return registrationRepo.save(user);
    }

    // Register the user by encoding the password and then saving the user
    public PatientEntity registerUser(PatientEntity user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return registrationRepo.save(user);  // Save to the database
    }

    // Find user by email
    public PatientEntity UserEmail(String email) {
        return registrationRepo.findByEmail(email).orElse(null);  // If not found, return null
    }

}

