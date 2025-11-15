package com.cts.user_service.controller;
import com.cts.user_service.dto.RideDetailsDto;
import com.cts.user_service.dto.UserLoginDto;
import com.cts.user_service.dto.UserDto;
import com.cts.user_service.entity.UserEntity;
import com.cts.user_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("user")
 public class UserController {
    @Autowired
    private UserService userService;
      
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDto loginData) {
        return userService.validateLoginData(loginData);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestBody UserDto forgotPasswordCredentials) {
        return userService.verifyForgotPasswordCredentials(forgotPasswordCredentials);
    }

    @PutMapping("/changepassword")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody UserEntity userNewPassword) {
        return userService.updateNewPassword(userNewPassword);
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/editprofile")
    public ResponseEntity<Map<String,String>> editUserProfileDetails(@RequestBody UserDto userProfile) {
        System.out.println("editUserProfileDetails :: controller");
        return userService.editUserProfileData(userProfile);
    }
    @GetMapping("/{userId}/confirmed")
    public ResponseEntity<Map<String, Object>> getConfirmedRequestsByUser( HttpServletRequest request,@PathVariable Long userId){
        String authHeader = request.getHeader("Authorization");
        return userService.getConfirmedRequestsByUser(authHeader,userId);
    }

    @PostMapping("/createride")
    public ResponseEntity<RideDetailsDto> createRide(
            HttpServletRequest request,
            @RequestBody RideDetailsDto rideDetails) {

        String authHeader = request.getHeader("Authorization"); // Full "Bearer <token>"
        return userService.createRideRequest(authHeader, rideDetails);
    }

    @GetMapping("/{userId}/request/{requestId}")
    public ResponseEntity<RideDetailsDto> getConfirmedRideForUser(HttpServletRequest request,@PathVariable Long userId,@PathVariable Long requestId){
        String authHeader = request.getHeader("Authorization");
        return userService.getConfirmedRideForUser(authHeader,userId,requestId);
    }

}
