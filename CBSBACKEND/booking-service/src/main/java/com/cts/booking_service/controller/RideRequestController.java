package com.cts.booking_service.controller;

import java.util.List;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.dto.RideHistory;
import com.cts.booking_service.service.RideRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ride")
public class RideRequestController {

    @Autowired
    private RideRequestService rideRequestService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<RideRequest> createRide(@RequestBody RideRequest request) {
       RideRequest rideDetails= rideRequestService.saveRideDetails(request);
       return ResponseEntity.ok(rideDetails);
    }
    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/pending")
    public List<RideRequest> getPendingRides() {
        return rideRequestService.getPendingRides();
    }
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping ("/accept/{requestId}/{driverId}")
    public ResponseEntity<RideRequest> acceptRide(@PathVariable Long requestId, @PathVariable Long driverId) {
        RideRequest updatedRequest = rideRequestService.acceptRequest(requestId, driverId);
        return ResponseEntity.ok(updatedRequest);
    }
    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver/{driverId}/completed")
    public ResponseEntity<List<RideRequest>> getCompletedRidesByDriver(@PathVariable Long driverId) {
        List<RideRequest> confirmedRides = rideRequestService.getConfirmedRidesByDriverId(driverId);
        return ResponseEntity.ok(confirmedRides);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}/confirmed")
    public ResponseEntity<List<RideHistory>> getConfirmedRequestsByUser(HttpServletRequest request, @PathVariable Long userId) {
        String authHeader=request.getHeader("Authorization");
        List<RideHistory> confirmedRequests = rideRequestService.getConfirmedRequestsByUserId(authHeader,userId);
        return ResponseEntity.ok(confirmedRequests);
    }
    @GetMapping("/all")
    public ResponseEntity<List<RideRequest>> getAllRequests() {
        return ResponseEntity.ok(rideRequestService.getAllRequests());
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}/request/{requestId}/confirmed")
    public ResponseEntity<RideRequest> getConfirmedRideForUser(
            @PathVariable Long userId,
            @PathVariable Long requestId) {
        RideRequest ride = rideRequestService.getConfirmedRideForUser(requestId, userId);
        return ResponseEntity.ok(ride);
    }

    //this is the new functionality  i have added
    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/{driverId}/ongoing")
    public ResponseEntity<List<RideRequest>> getOngoingRidesByDriver(@PathVariable Long driverId) {
        List<RideRequest> ongoingRides = rideRequestService.getOngoingRidesByDriver(driverId);
        return ResponseEntity.ok(ongoingRides);
    }
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("/completeride/{rideId}/{driverId}")
    public ResponseEntity<RideRequest> completeRide(@PathVariable Long rideId, @PathVariable Long driverId) {
        RideRequest completedRide = rideRequestService.completeRide(rideId, driverId);
        return ResponseEntity.ok(completedRide);
    }



}


