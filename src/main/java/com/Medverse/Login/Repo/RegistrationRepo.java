package com.Medverse.Login.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Medverse.Login.Entity.RegistrationEntity;

@Repository
public interface RegistrationRepo extends JpaRepository<RegistrationEntity, Long> {  // Use Integer as the type for the ID

    Optional<RegistrationEntity> findByEmail(String email);  // Find user by email (case-sensitive by default)
//    forget password...................................................................................
 
    Optional<RegistrationEntity> findByResetPasswordToken(String token);
//..........................................................................................................

}