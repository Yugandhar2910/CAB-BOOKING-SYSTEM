package com.cts.cabproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLoginDto {
    private String name;
    private String email;
    private String password;
}
