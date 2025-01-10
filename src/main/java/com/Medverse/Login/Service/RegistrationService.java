package com.Medverse.Login.Service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Medverse.Login.Entity.RegistrationEntity;
import com.Medverse.Login.Repo.RegistrationRepo;

@Service
public class RegistrationService {

    private final RegistrationRepo registrationRepo; 
    private final PasswordEncoder passwordEncoder;  //BcryptoPasswordEncoder --> 

    public RegistrationService(RegistrationRepo registrationRepo, PasswordEncoder passwordEncoder) {
        this.registrationRepo = registrationRepo;
        this.passwordEncoder = passwordEncoder;
    } 

    // Save the RegistrationEntity without encoding the password
    public RegistrationEntity save(RegistrationEntity user) {
        return registrationRepo.save(user);
    }

    // Register the user by encoding the password and then saving the user
    public RegistrationEntity registerUser(RegistrationEntity user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return registrationRepo.save(user);  // Save to the database
    }

    // Find user by email
    public RegistrationEntity UserEmail(String email) {
        return registrationRepo.findByEmail(email).orElse(null);  // If not found, return null
    }
//    forgetpassword...........................................................................................................................
    public void createPasswordResetToken(RegistrationEntity user, String token) {
        user.setResetPasswordToken(token);
        user.setTokenExpirationTime(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        registrationRepo.save(user);
    }

    public RegistrationEntity findUserByResetPasswordToken(String token) {
        return registrationRepo.findByResetPasswordToken(token).orElse(null);
    }

    public boolean isTokenExpired(RegistrationEntity user) {
        return user.getTokenExpirationTime() == null || user.getTokenExpirationTime().isBefore(LocalDateTime.now());
    }

    public void updatePassword(RegistrationEntity user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null); // Clear the token
        user.setTokenExpirationTime(null);
        registrationRepo.save(user);
    }
//    ...............................................................................................................................................
}
