package com.cts.cabproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDetailsDto {

    private Long requestId;
    private String origin;
    private String destination;
    private double amount;
    private String distance;
    private Long userId;
    private boolean isAccepted;
    private Long acceptedDriverId;
    private String status;

}
