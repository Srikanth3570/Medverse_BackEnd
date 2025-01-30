package com.Medverse.Login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medverse.Login.Entity.PatientEntity;
import com.Medverse.Login.Service.RegistrationService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/home")
public class PatientController {
    
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;
    
    public PatientController(RegistrationService registrationService, PasswordEncoder passwordEncoder) {
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody  PatientEntity user, BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // Wrap errors in a JSON structure
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "error", "errors", errors));
        }

        // Check if user already exists
        if (registrationService.UserEmail(user.getEmail()) != null) {
            // Return JSON error message
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", "error", "message", "User already exists!"));
        }

        // Encode password and save user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        registrationService.save(user);

        // Convert to DTO and return success response
      
        return ResponseEntity.ok(Map.of("status", "success", "data", user));
    }
   
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody PatientEntity user) {
        // Log input data
        System.out.println("Attempting login for email: " + user.getEmail());

        // Check if user exists
        PatientEntity foundUser = registrationService.UserEmail(user.getEmail());
        if (foundUser == null) {
            System.out.println("User not found for email: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

        // Verify password
        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
//            UserDTO userDTO = new UserDTO(foundUser); // Convert entity to DTO
            return ResponseEntity.ok(Map.of("status", "success", "data", foundUser));
        } else {
            System.out.println("Password mismatch for email: " + user.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }

}

