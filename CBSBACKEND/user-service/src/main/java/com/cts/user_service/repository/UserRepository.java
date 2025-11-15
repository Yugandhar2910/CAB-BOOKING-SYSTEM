package com.cts.user_service.repository;

import com.cts.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findByUserNameOrUserEmailOrUserPhoneNumber(String userName, String email, String phone);
    Optional<UserEntity> findByUserEmail(String userName);
    Optional<UserEntity> findByUserEmailAndUserPhoneNumber(String userEmail,String userPhoneNumber);
    Optional<UserEntity> findByUserId(Long userId);

}
