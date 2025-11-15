package com.cts.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideHistory {
    private String origin;
    private String destination;
    private String driverName;
    private double amount;
    private String status;
    private String method;
}