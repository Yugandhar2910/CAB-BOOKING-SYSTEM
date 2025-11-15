package com.cts.cabproject.controller;

import com.cts.cabproject.dto.RideDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.cabproject.entity.Driver;
import com.cts.cabproject.service.DriverService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/driver")
public class DriverController {
    
    @Autowired
    private DriverService driverService;
    
    // Driver Registration - Public endpoint (no authentication required)
    @PostMapping("/register")
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {
        // Auto-verify new drivers and set them as available
        driver.setVerified(true);
        driver.setAvailable(true);
        return ResponseEntity.ok(driverService.registerDriver(driver));
    }
    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("getProfileDetails/{DriverId}")
    public ResponseEntity<Driver> profileView(@PathVariable Long DriverId){
        return ResponseEntity.ok(driverService.searchByID(DriverId));
    }
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/update")
    public ResponseEntity<Driver> updateProfile(@RequestBody Driver driver){
        return ResponseEntity.ok(driverService.updateProfile(driver));
    }
  
    // Protected endpoints - require JWT authentication
    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("check/{driverId}/{isAvailable}")
    public ResponseEntity<String>  toggleAvailability(@PathVariable Long driverId, @PathVariable boolean isAvailable) {
        driverService.updateAvailability(driverId, isAvailable);
        return ResponseEntity.ok("Availability updated");

    }
    @PostMapping("/forgotpassword")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody Driver forgotPasswordCredentials) {
        return driverService.verifyForgotPasswordCredentials(forgotPasswordCredentials);
    }
    @PutMapping("/changepassword")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Driver driverNewPassword) {
        return driverService.updateNewPassword(driverNewPassword);
    }
    @PostMapping ("/acceptride/{requestId}/{driverId}")
    public ResponseEntity<RideDetailsDto> acceptRide(HttpServletRequest request, @PathVariable Long requestId, @PathVariable Long driverId){
            String authHeader = request.getHeader("Authorization");
             return driverService.acceptRide(authHeader,requestId,driverId);
    }
    @GetMapping("/{driverId}/completed")
    public ResponseEntity<List<RideDetailsDto>> getCompletedRidesByDriver(HttpServletRequest request,@PathVariable Long driverId) {
        String authHeader = request.getHeader("Authorization");
        List<RideDetailsDto> confirmedRides = driverService.getCompletedRidesByDriverId(authHeader,driverId);
        return ResponseEntity.ok(confirmedRides);
    }

    @GetMapping("/getdrivername/{driverId}")
    public String getDriverNameByDriverId(@PathVariable Long driverId){
        System.out.println("driver name fetching");
        return  driverService.getDriverNameByDriverId(driverId);
    }

    @GetMapping("/pending")
    public List<RideDetailsDto> getPendingRides(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        return driverService.getPendingRides(authHeader);
    }
    @GetMapping("/{driverId}")
    public Driver getDriverById(@PathVariable Long driverId){
        return driverService.getByDriverId(driverId);
    }

    @GetMapping("/{driverId}/ongoing")
    public ResponseEntity<List<RideDetailsDto>> getOngoingRidesByDriver(HttpServletRequest request,@PathVariable Long driverId){
        String authHeader=request.getHeader("Authorization");
        return driverService.getOngoingRidesByDriver(authHeader,driverId);
    }
    @PostMapping("/completeride/{rideId}/{driverId}")
    public ResponseEntity<RideDetailsDto> completeRide(HttpServletRequest request,@PathVariable Long rideId, @PathVariable Long driverId){
        String authHeader=request.getHeader("Authorization");
        return driverService.completeRide(authHeader,rideId,driverId);
    }

}


