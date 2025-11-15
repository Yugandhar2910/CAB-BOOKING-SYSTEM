package com.cts.booking_service.service;

import com.cts.booking_service.Entity.RideRequest;
import com.cts.booking_service.client.DriverAndPaymentClient;
import com.cts.booking_service.dto.RideHistory;
import com.cts.booking_service.exceptions.RideRequestNotFoundException;
import com.cts.booking_service.repository.RideRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    private static final Logger logger = LoggerFactory.getLogger(RideRequestServiceImpl.class);

    @Autowired
    private RideRequestRepository rideRepo;

    @Autowired
    private DriverAndPaymentClient client;

    @Override
    public RideRequest saveRideDetails(RideRequest rideDetails) {
        rideDetails.setStatus("PENDING");
        rideDetails.setAccepted(false);
        rideDetails.setAcceptedDriverId(null);

        logger.info("Saving new ride request for user ID: {}", rideDetails.getUserId());
        return rideRepo.save(rideDetails);
    }

    @Override
    public List<RideRequest> getPendingRides() {
        logger.info("Fetching all pending ride requests");
        return rideRepo.findByStatus("PENDING");
    }

    @Override
    public RideRequest acceptRequest(Long requestId, Long driverId) {
        logger.info("Driver ID {} is attempting to accept ride request ID {}", driverId, requestId);

        RideRequest request = rideRepo.findById(requestId)
                .orElseThrow(() -> new RideRequestNotFoundException("Ride request with ID " + requestId + " not found"));

        if (request.isAccepted()) {
            logger.warn("Ride request ID {} has already been accepted by driver ID {}", requestId, request.getAcceptedDriverId());
            throw new RideRequestNotFoundException("Ride request ID " + requestId + " has already been accepted by another driver.");
        }

        request.setAccepted(true);
        request.setAcceptedDriverId(driverId);
        request.setStatus("ONGOING");

        logger.info("Ride request ID {} accepted by driver ID {}", requestId, driverId);
        return rideRepo.save(request);
    }

    @Override
    public List<RideRequest> getConfirmedRidesByDriverId(Long driverId) {
        logger.info("Fetching latest 10 completed rides for driver ID {}", driverId);
        Pageable topTen = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "requestId"));
        return rideRepo.findByAcceptedDriverIdAndStatusOrderByRequestIdDesc(driverId, "COMPLETED", topTen).getContent();
    }
    

    @Override
    public List<RideHistory> getConfirmedRequestsByUserId(String token, Long userId) {
        List<RideRequest> details = rideRepo.findByUserIdAndStatus(userId, "COMPLETED");
        List<RideHistory> history = new ArrayList<>();

        if (details != null) {
            for (RideRequest request : details) {
                RideHistory history1 = new RideHistory();
                history1.setAmount(request.getAmount());
                history1.setOrigin(request.getOrigin());
                history1.setDestination(request.getDestination());

                // Handle payment details safely
                try {
                    List<String> paymentDetails = client.getPaymentDetailsForRide(token, request.getRequestId());
                    history1.setMethod(paymentDetails.size() > 1 ? paymentDetails.get(1) : "Unavailable");
                    history1.setStatus(paymentDetails.size() > 0 ? paymentDetails.get(0) : "Unknown");
                } catch (Exception e) {
                    history1.setMethod("Unavailable");
                    history1.setStatus("Unknown");
                }

                // Handle driver name safely
                try {
                    String driverName = client.getDriverNameByDriverId(token, request.getAcceptedDriverId());
                    history1.setDriverName(driverName != null ? driverName : "null");
                } catch (Exception e) {
                    history1.setDriverName("null");
                }

                history.add(history1);
            }
        }

        return history;
    }




    @Override
    public List<RideRequest> getAllRequests() {
        logger.info("Fetching all ride requests");
        return rideRepo.findAll();
    }

    @Override
    public RideRequest getConfirmedRideForUser(Long requestId, Long userId) {
        logger.info("Fetching confirmed ride for user ID {} and request ID {}", userId, requestId);
        List<RideRequest> rides = rideRepo.findByRequestIdAndUserId(requestId, userId);

        if (rides.isEmpty()) {
            throw new RideRequestNotFoundException("No confirmed ride found for user ID " + userId + " and request ID " + requestId);
        }

        return rides.getFirst();
    }


    //this is the new code impl
    @Override
    public List<RideRequest> getOngoingRidesByDriver(Long driverId) {
        logger.info("Fetching ongoing rides for driver ID {}", driverId);
        return rideRepo.findByAcceptedDriverIdAndStatus(driverId, "ONGOING");
    }

    @Override
    public RideRequest completeRide(Long rideId, Long driverId) {
        logger.info("Completing ride ID {} for driver ID {}", rideId, driverId);

        RideRequest ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RideRequestNotFoundException("Ride with ID " + rideId + " not found"));

        if (!ride.getAcceptedDriverId().equals(driverId)) {
            throw new RideRequestNotFoundException("Driver ID " + driverId + " is not authorized to complete this ride.");
        }

        if (!"ONGOING".equalsIgnoreCase(ride.getStatus())) {
            throw new RideRequestNotFoundException("Ride ID " + rideId + " is not in ONGOING status.");
        }

        ride.setStatus("COMPLETED");
        logger.info("Ride ID {} marked as COMPLETED by driver ID {}", rideId, driverId);
        return rideRepo.save(ride);
    }

}