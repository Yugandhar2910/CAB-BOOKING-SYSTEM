package com.cts.rating_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver_reviewss")
public class DriverReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    
    @Column(nullable = false)
    private Long driverId;
    
    
    @Column(nullable = false)
    private String reviewText;
    
    @Column(nullable = false)
    private Integer rating; 
    
   
    
}