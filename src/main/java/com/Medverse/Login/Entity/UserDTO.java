package com.Medverse.Login.Entity;

import java.time.LocalDate;

import com.Medverse.Login.Entity.RegistrationEntity.Gender;
import com.Medverse.Login.Entity.RegistrationEntity.Role;

public class UserDTO {
	    private Long userId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private LocalDate dob;
	    private String phoneNumber;
	    private Gender gender;
	    private Role role;
	    private String profilePic;
		private String password;
		private String address;

		// Constructor
	    public UserDTO(RegistrationEntity entity) {
                        this.userId = entity.getUserId();
                        this.firstName = entity.getFirstName();
                        this.lastName = entity.getLastName();
                        this.email = entity.getEmail();
                        this.password = entity.getPassword();
                        this.phoneNumber = entity.getPhoneNumber();
                        this.address = entity.getAddress();
                        this.dob = entity.getDob();
                        this.gender = entity.getGender();
                        this.profilePic = entity.getProfilePic();
                        this.role = entity.getRole();
}
	    
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

		public LocalDate getDob() {
			return dob;
		}

		public void setDob(LocalDate dob) {
			this.dob = dob;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public Gender getGender() {
			return gender;
		}

		public void setGender(Gender gender) {
			this.gender = gender;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public String getProfilePic() {
			return profilePic;
		}

		public void setProfilePic(String profilePic) {
			this.profilePic = profilePic;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		
	    // Getters and setters (optional, if required)
	}