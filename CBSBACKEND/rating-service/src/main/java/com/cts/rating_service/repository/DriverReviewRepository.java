package com.cts.rating_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.rating_service.entity.DriverReview;


@Repository
public interface DriverReviewRepository extends JpaRepository<DriverReview, Long> {
    
    List<DriverReview> findByDriverId(Long driverId);
    
    @Query("SELECT AVG(dr.rating) FROM DriverReview dr WHERE dr.driverId = :driverId")
    Double findAverageRatingByDriverId(@Param("driverId") Long driverId);
    
}
