package com.cts.booking_service.service;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.dto.RideHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RideRequestService {
    RideRequest saveRideDetails(RideRequest rideDetails);
    List<RideRequest> getPendingRides();
    RideRequest acceptRequest(Long requestId, Long driverId);
    List<RideRequest> getConfirmedRidesByDriverId(Long driverId);
    List<RideHistory> getConfirmedRequestsByUserId(String token,Long userId);
    List<RideRequest> getAllRequests();
    public RideRequest getConfirmedRideForUser(Long requestId, Long userId);

    //new code
    List<RideRequest> getOngoingRidesByDriver(Long driverId);
    RideRequest completeRide(Long rideId, Long driverId);

}
