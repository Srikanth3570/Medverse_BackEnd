package com.Medverse.Login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

import com.Medverse.Login.Entity.RegistrationEntity;
import com.Medverse.Login.Entity.UserDTO;
import com.Medverse.Login.Service.RegistrationService;

import jakarta.validation.Valid;

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
    public ResponseEntity<Object> register(@Valid @RequestBody RegistrationEntity user, BindingResult bindingResult) {
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
        RegistrationEntity savedUser = registrationService.save(user);

        // Convert to DTO and return success response
        UserDTO userDTO = new UserDTO(savedUser);
        return ResponseEntity.ok(Map.of("status", "success", "data", userDTO));
    }

    // @PostMapping("/login")
    // public ResponseEntity<String> login(@RequestBody RegistrationEntity user) {
    // RegistrationEntity foundUser =
    // registrationService.UserEmail(user.getEmail());
    //
    // if (foundUser != null && passwordEncoder.matches(user.getPassword(),
    // foundUser.getPassword())) {
    // return ResponseEntity.ok("Login successful!");
    // }
    // return ResponseEntity.status(401).body("Invalid email or password");
    // }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody RegistrationEntity user) {
        // Log input data
        System.out.println("Attempting login for email: " + user.getEmail());

        // Check if user exists
        RegistrationEntity foundUser = registrationService.UserEmail(user.getEmail());
        if (foundUser == null) {
            System.out.println("User not found for email: " + user.getEmail());
            // Return JSON error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }

        // Verify password
        if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            // Convert to DTO
            UserDTO userDTO = new UserDTO(foundUser); // Assuming UserDTO constructor takes a RegistrationEntity

            // Return success response with userDTO
            return ResponseEntity.ok(Map.of("status", "success", "data", userDTO));
        } else {
            System.out.println("Password mismatch for email: " + user.getEmail());
            // Return JSON error response for password mismatch
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }

    // forget
    // password............................................................................................................

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgotPassword(@RequestBody String email) {
        System.out.println("Received email: " + email); // Debugging line
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
    // .........................................................................................................................

}

