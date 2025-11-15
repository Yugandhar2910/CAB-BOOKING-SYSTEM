package com.cts.cabproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CabDrivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private String vehicleModel;

    @Column(unique = true, nullable = false)
    private String vehicleRegNo;

    @Column(nullable = false)
    private String vehicleColor;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private boolean isAvailable;

    @Column(nullable = false)
    private boolean isVerified ;

}