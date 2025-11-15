package com.cts.booking_service.exceptions;

public class RideRequestNotFoundException extends RuntimeException {
    public RideRequestNotFoundException(String message) {
        super(message);
    }
}
