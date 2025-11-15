package com.cts.rating_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.rating_service.entity.DriverReview;
import com.cts.rating_service.repository.DriverReviewRepository;

@Service
public class DriverReviewServiceImpl implements DriverReviewService {
    
    @Autowired
    private DriverReviewRepository reviewRepository;
    
    @Override
    public DriverReview addReview(DriverReview review) {
        return reviewRepository.save(review);
    }
    
    @Override
    public List<DriverReview> getReviewsByDriverId(Long driverId) {
        return reviewRepository.findByDriverId(driverId);
    }
    
    @Override
    public Double getAverageRating(Long driverId) {
        return reviewRepository.findAverageRatingByDriverId(driverId);
    }
    
}
