package com.cts.user_service.client;

import com.cts.user_service.dto.RideDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="api-gateway")
public interface RideBookingClient {
    @PostMapping("booking-api/ride/create")
    public ResponseEntity<RideDetailsDto> createRide(@RequestHeader("Authorization") String token, @RequestBody RideDetailsDto request);
    @GetMapping("booking-api/ride/user/{userId}/confirmed")
    public ResponseEntity<List<RideDetailsDto>> getConfirmedRequestsByUser(@RequestHeader("Authorization") String token,@PathVariable Long userId);
    @GetMapping("booking-api/ride/user/{userId}/request/{requestId}/confirmed")
    public ResponseEntity<RideDetailsDto> getConfirmedRideForUser(@RequestHeader("Authorization") String token,@PathVariable Long userId,@PathVariable Long requestId);

}