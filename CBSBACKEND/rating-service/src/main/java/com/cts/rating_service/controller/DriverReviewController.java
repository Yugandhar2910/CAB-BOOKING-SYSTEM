package com.cts.rating_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.rating_service.entity.DriverReview;
import com.cts.rating_service.service.DriverReviewService;

@RestController
@RequestMapping("/reviews")
public class DriverReviewController {
    
    @Autowired
    private DriverReviewService reviewService;
    

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{driverId}")
    public ResponseEntity<DriverReview> addReview(@RequestBody DriverReview review, @PathVariable("driverId") Long driverId) {
        review.setDriverId(driverId); 
        DriverReview savedReview = reviewService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<DriverReview>> getDriverReviews(@PathVariable Long driverId) {
        List<DriverReview> reviews = reviewService.getReviewsByDriverId(driverId);
        return ResponseEntity.ok(reviews);
    }
    @PreAuthorize("hasRole('USER') or hasRole('DRIVER')")
    @GetMapping("/driver/{driverId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long driverId) {
        Double averageRating = reviewService.getAverageRating(driverId);
        return ResponseEntity.ok(averageRating != null ? averageRating : 0.0);
    }
    
    
}
