package com.cts.booking_service.Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RideRequestTest {

    @Test
    void testNoArgsConstructor() {
        RideRequest ride = new RideRequest();
        assertNotNull(ride);
    }

    @Test
    void testAllArgsConstructor() {
        RideRequest ride = new RideRequest(
                1L,
                "Mumbai",
                "Pune",
                500.0,
                "150km",
                101L,
                true,
                202L,
                "COMPLETED"
        );

        assertEquals(1L, ride.getRequestId());
        assertEquals("Mumbai", ride.getOrigin());
        assertEquals("Pune", ride.getDestination());
        assertEquals(500.0, ride.getAmount());
        assertEquals("150km", ride.getDistance());
        assertEquals(101L, ride.getUserId());
        assertTrue(ride.isAccepted());
        assertEquals(202L, ride.getAcceptedDriverId());
        assertEquals("COMPLETED", ride.getStatus());
    }

    @Test
    void testDefaultValues() {
        RideRequest ride = new RideRequest();
        assertFalse(ride.isAccepted());
        assertNull(ride.getAcceptedDriverId());
        assertEquals("PENDING", ride.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        RideRequest ride = new RideRequest();
        ride.setOrigin("Delhi");
        ride.setDestination("Agra");
        ride.setAmount(300.0);
        ride.setDistance("200km");
        ride.setUserId(102L);
        ride.setAccepted(true);
        ride.setAcceptedDriverId(203L);
        ride.setStatus("CONFIRMED");

        assertEquals("Delhi", ride.getOrigin());
        assertEquals("Agra", ride.getDestination());
        assertEquals(300.0, ride.getAmount());
        assertEquals("200km", ride.getDistance());
        assertEquals(102L, ride.getUserId());
        assertTrue(ride.isAccepted());
        assertEquals(203L, ride.getAcceptedDriverId());
        assertEquals("CONFIRMED", ride.getStatus());
    }
}
