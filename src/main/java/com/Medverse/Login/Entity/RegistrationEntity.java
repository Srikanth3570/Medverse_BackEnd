package com.Medverse.Login.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Registration")
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "First name is required")
    @Column(nullable = false, length = 255)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false, length = 255)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid Email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 255)
    private String password;

    @NotBlank(message = "Phone Number is required")
    @Column(nullable = false, length = 50)
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Column(nullable = false, length = 255)
    private String address;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private String profilePic;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
 
    
    
//    forgetpassword details...........................................................................................
    
    private String resetPasswordToken;
    private LocalDateTime tokenExpirationTime;

    // Getters and Setters for new fields
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public LocalDateTime getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(LocalDateTime tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }
//    ..............................................................................................................
    
        
    // Parameterized Constructor
    public RegistrationEntity(Long userId, String firstName, String lastName, String email, String password,
                              String phoneNumber, String address, LocalDate dob, Gender gender, String profilePic, Role role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
        this.role = role;
    }
      
    public RegistrationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // toString() method for better debugging
    @Override
    public String toString() {
        return "RegistrationEntity [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + ", address="
                + address + ", dob=" + dob + ", gender=" + gender + ", profilePic=" + profilePic + ", role=" + role
                + "]";
    }

    // Enum Definitions
    public enum Gender {
        Male, Female, Other
    }

    public enum Role {
        Patient, Doctor, Admin
    }
}
