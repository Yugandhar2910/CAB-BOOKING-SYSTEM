package com.cts.rating_service.service;

import java.util.List;

import com.cts.rating_service.entity.DriverReview;


public interface DriverReviewService {
    
    DriverReview addReview(DriverReview review);
    
    List<DriverReview> getReviewsByDriverId(Long driverId);
    
    Double getAverageRating(Long driverId);
    
   
}
