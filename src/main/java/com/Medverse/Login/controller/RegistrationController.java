package com.Medverse.Login.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.RegistrationEntity;
import com.Medverse.Login.Service.RegistrationService;

@RestController
@CrossOrigin
@RequestMapping("/home")
public class RegistrationController {
    
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;
    
    public RegistrationController(RegistrationService registrationService, PasswordEncoder passwordEncoder) {
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationEntity user) {
        if (registrationService.UserEmail(user.getEmail()) != null) {
            return ResponseEntity.status(409).body("User already exists!");
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user with the encoded password
        RegistrationEntity savedUser = registrationService.save(user);
        return ResponseEntity.ok("Registration successful with ID: " + savedUser.getUserId());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RegistrationEntity user) {
        RegistrationEntity foundUser = registrationService.UserEmail(user.getEmail());

        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
   

    
    
//    forget password............................................................................................................

    
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        System.out.println("Received email: " + email);  // Debugging line
        RegistrationEntity user = registrationService.UserEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        String token = UUID.randomUUID().toString();
        registrationService.createPasswordResetToken(user, token);

        // Logging the generated token
        System.out.println("Generated reset token: " + token);

        // Replace this with actual email service logic
        System.out.println("Password reset link: http://localhost:8080/home/reset-password?token=" + token);

        return ResponseEntity.ok("Password reset link sent to your email.");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Invalid request");
        }

        RegistrationEntity user = registrationService.findUserByResetPasswordToken(token);
        if (user == null || registrationService.isTokenExpired(user)) {
            return ResponseEntity.status(400).body("Invalid or expired token");
        }

        registrationService.updatePassword(user, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
//    .........................................................................................................................
    
}
