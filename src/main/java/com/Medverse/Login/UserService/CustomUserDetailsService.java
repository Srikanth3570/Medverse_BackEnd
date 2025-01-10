package com.Medverse.Login.UserService;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Medverse.Login.Entity.RegistrationEntity;
import com.Medverse.Login.Repo.RegistrationRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RegistrationRepo registrationRepo;

    public CustomUserDetailsService(RegistrationRepo registrationRepo) {   
        this.registrationRepo = registrationRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user based on the email (username)
        RegistrationEntity registrationEntity = registrationRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Return a Spring Security User object with roles and encrypted password
        return User.builder()
                   .username(registrationEntity.getEmail())
                   .password(registrationEntity.getPassword())  // Ensure password is encoded
                   .roles(registrationEntity.getRole().name())  // Role is taken from the RegistrationEntity
                   .build();
    }
}
