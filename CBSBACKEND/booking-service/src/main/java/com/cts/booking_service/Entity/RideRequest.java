package com.cts.booking_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RideReq")
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String distance;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean isAccepted = false;

    @Column(nullable = true)
    private Long acceptedDriverId = null;

    @Column(nullable = false)
    private String status = "PENDING";
}
