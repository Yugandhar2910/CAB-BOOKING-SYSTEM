package com.cts.cabproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cts.cabproject.client.RideBookingClient;
import com.cts.cabproject.dto.RideDetailsDto;
import com.cts.cabproject.exceptions.AuthenticationException;
import com.cts.cabproject.exceptions.ForgotPasswordVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.cabproject.entity.Driver;
import com.cts.cabproject.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {
    
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RideBookingClient rideClient;

    @Override
    public ResponseEntity<RideDetailsDto> acceptRide(String token,Long rideId, Long driverId) {
        return rideClient.acceptRide(token,rideId,driverId);
    }

    @Override
    public Driver registerDriver(Driver driver) {
        // Check if email already exists
        if (driverRepository.existsByEmail(driver.getEmail())) {
            throw new RuntimeException("Driver with this email already exists");
        }
        
        // Check if license number already exists
        if (driverRepository.existsByLicenseNumber(driver.getLicenseNumber())) {
            throw new RuntimeException("Driver with this license number already exists");
        }
        driver.setPasswordHash(passwordEncoder.encode(driver.getPasswordHash()));
        
        return driverRepository.save(driver);
    }
    
    @Override
    public Driver searchByID(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + driverId));
    }
    
    @Override
    public Driver updateProfile(Driver driver) {
        Driver existingDriver = searchByID(driver.getDriverId());
        
        // Update fields
        existingDriver.setFullName(driver.getFullName());
        existingDriver.setPhoneNumber(driver.getPhoneNumber());
        existingDriver.setVehicleColor(driver.getVehicleColor());
        existingDriver.setPhoneNumber(driver.getPhoneNumber());
        existingDriver.setCapacity(driver.getCapacity());
        
        return driverRepository.save(existingDriver);
    }
    
    @Override
    public void deleteDriver(Long driverId) {
        Driver driver = searchByID(driverId);
        driverRepository.delete(driver);
    }
    
    @Override
    public List<Driver> getAllDrivers(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Driver> driverPage = driverRepository.findAll(pageable);
        return driverPage.getContent();
    }

    @Override
    public void updateAvailability(Long driverId, boolean isAvailable) {
        System.out.println("pacthmapping");
        Driver driver = searchByID(driverId);
        driver.setAvailable(isAvailable);
        driverRepository.save(driver);
    }

@Override
public Driver validateDriverLogin(String email, String password) {
    Optional<Driver> driverOpt = driverRepository.findByEmail(email);
    if (driverOpt.isPresent()) {
        Driver driver = driverOpt.get();
        if (passwordEncoder.matches(password, driver.getPasswordHash())) {
            return driver;
        }
    }
    return null;
}


    @Override
    public Driver findDriverByEmailAndPassword(String email, String passwordHash) {
        Optional<Driver> driverOpt = driverRepository.findByEmailAndPasswordHash(email, passwordHash);
        return driverOpt.orElse(null);
    }

    @Override
    public ResponseEntity<Map<String, String>> verifyForgotPasswordCredentials(Driver forgotPasswordCredentials) {
        Optional<Driver> driver = driverRepository.findByEmailAndPhoneNumber(
                forgotPasswordCredentials.getEmail(),
                forgotPasswordCredentials.getPhoneNumber()
        );

        if (driver.isEmpty()) {
            throw new ForgotPasswordVerificationException("Invalid email or phone number");
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "OTP sent successfully");
        response.put("driver_id", String.valueOf(driver.get().getDriverId()));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Map<String, String>> updateNewPassword(Driver driverNewPassword) {
        Optional<Driver> optionalDriver = driverRepository.findByDriverId(driverNewPassword.getDriverId());

        if (optionalDriver.isEmpty()) {
            throw new AuthenticationException("Driver not found");
        }

        Driver existingDriver = optionalDriver.get();
        String encodedPassword = passwordEncoder.encode(driverNewPassword.getPasswordHash());
        existingDriver.setPasswordHash(encodedPassword);

        driverRepository.save(existingDriver);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Password updated successfully");

        return ResponseEntity.ok(response);
    }

    @Override
    public List<RideDetailsDto> getCompletedRidesByDriverId(String token ,Long driverId) {
        return rideClient.getCompletedRidesByDriver(token,driverId).getBody();
    }

    @Override
    public List<RideDetailsDto> getPendingRides(String token) {
        return rideClient.getPendingRides(token);
    }

    @Override
    public Driver getByDriverId(Long driverId) {
        Optional <Driver> driver= driverRepository.findByDriverId(driverId);
        Driver existingDriver = driver.get();
        return existingDriver;

    }

    @Override
    public String getDriverNameByDriverId(Long driverId) {
        Optional<Driver>  driver=driverRepository.findByDriverId(driverId);
        if(driver.isPresent()){
            return driver.get().getFullName();
        }
        return null;
    }

    @Override
    public ResponseEntity<List<RideDetailsDto>> getOngoingRidesByDriver(String token,Long driverId) {
        return rideClient.getOngoingRidesByDriver(token,driverId);
    }

    @Override
    public ResponseEntity<RideDetailsDto> completeRide(String token,Long rideId, Long driverId) {
        return rideClient.completeRide(token,rideId,driverId);
    }
}
