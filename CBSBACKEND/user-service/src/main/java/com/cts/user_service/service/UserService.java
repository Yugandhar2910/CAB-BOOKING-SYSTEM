package com.cts.user_service.service;


import com.cts.user_service.dto.RideDetailsDto;
import com.cts.user_service.dto.UserLoginDto;
import com.cts.user_service.dto.UserDto;
import com.cts.user_service.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

public interface UserService {
    ResponseEntity<Map<String, String>> registerUser(UserDto user);
    ResponseEntity<Map<String, Object>> validateLoginData(UserLoginDto loginData);
    ResponseEntity <Map<String, String>> verifyForgotPasswordCredentials(UserDto forgotPasswordCredentials);
    ResponseEntity<Map<String,String>> updateNewPassword(UserEntity userNewPassword);

    ResponseEntity<Map<String, String>> editUserProfileData(UserDto userProfile);
    ResponseEntity<Map<String, Object>> getConfirmedRequestsByUser(String token,Long userId);
    ResponseEntity<RideDetailsDto> createRideRequest(String token,RideDetailsDto rideDetails);
    ResponseEntity<RideDetailsDto> getConfirmedRideForUser(String token,@PathVariable Long userId, @PathVariable Long requestId);
}
