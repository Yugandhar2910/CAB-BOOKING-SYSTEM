package com.cts.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name="user_name",unique = true,nullable = false)
    private String userName;
    @Column(name="user_email",unique = true,nullable = false)
    private String userEmail;
    @Column(name="user_phone_number",unique = true,nullable = false)
    private String userPhoneNumber;
    private String userPassword;

}
