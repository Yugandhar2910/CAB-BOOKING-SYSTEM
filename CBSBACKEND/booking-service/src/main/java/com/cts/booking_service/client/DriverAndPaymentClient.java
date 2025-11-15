package com.cts.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name="api-gateway")
public interface DriverAndPaymentClient {
    @GetMapping("driver-api/driver/getdrivername/{driverId}")
    public String getDriverNameByDriverId(@RequestHeader("Authorization") String token, @PathVariable Long driverId);
    @GetMapping("payment-api/payment/getpaymentstatusforride/{requestId}")
    public List<String> getPaymentDetailsForRide(@RequestHeader("Authorization") String token, @PathVariable Long requestId);
}

