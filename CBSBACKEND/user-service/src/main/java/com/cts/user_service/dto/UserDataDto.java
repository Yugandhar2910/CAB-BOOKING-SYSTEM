package com.cts.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;

}
