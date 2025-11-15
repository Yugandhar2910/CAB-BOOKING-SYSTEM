package com.cts.cabproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.cabproject.entity.Driver;
import com.cts.cabproject.security.JwtUtil;
import com.cts.cabproject.service.DriverService;

@RestController
@RequestMapping("driver/auth")
public class AuthController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Driver driver = driverService.validateDriverLogin(email, password);
        if (driver != null) {

           List<String> roles= List.of("ROLE_DRIVER");

            String token = jwtUtil.generateToken(email,roles);
            return ResponseEntity.ok(Map.of("token", token, "driver", driver));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
