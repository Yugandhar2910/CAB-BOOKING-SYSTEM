package com.cts.cabproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.cabproject.entity.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    
    Optional<Driver> findByEmail(String email);
    
    Optional<Driver> findByLicenseNumber(String licenseNumber);
    
    @Query("SELECT d FROM Driver d WHERE d.email = :email AND d.passwordHash = :passwordHash")
    Optional<Driver> findByEmailAndPasswordHash(@Param("email") String email, @Param("passwordHash") String passwordHash);
    
    boolean existsByEmail(String email);
    
    boolean existsByLicenseNumber(String licenseNumber);
    Optional<Driver> findByEmailAndPhoneNumber(String email,String phoneNumber);
    Optional<Driver> findByDriverId(Long driverId);

}
