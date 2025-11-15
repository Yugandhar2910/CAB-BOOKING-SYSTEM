package com.cts.cabproject.client;

import com.cts.cabproject.dto.RideDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="api-gateway")
public interface RideBookingClient {
    @GetMapping("booking-api/ride/pending")
    public List<RideDetailsDto> getPendingRides(@RequestHeader("Authorization") String token);
    @PostMapping("booking-api/ride/accept/{requestId}/{driverId}")
    public ResponseEntity<RideDetailsDto> acceptRide(@RequestHeader("Authorization") String token, @PathVariable Long requestId, @PathVariable Long driverId) ;
    @GetMapping("booking-api/ride/driver/{driverId}/completed")
    public ResponseEntity<List<RideDetailsDto>> getCompletedRidesByDriver(@RequestHeader("Authorization") String token,@PathVariable Long driverId) ;

    //new client
    @GetMapping("booking-api/ride/{driverId}/ongoing")
    public ResponseEntity<List<RideDetailsDto>> getOngoingRidesByDriver(@RequestHeader("Authorization") String token,@PathVariable Long driverId);

    @PostMapping("booking-api/ride/completeride/{rideId}/{driverId}")
    public ResponseEntity<RideDetailsDto> completeRide(@RequestHeader("Authorization") String token,@PathVariable Long rideId, @PathVariable Long driverId);
}
