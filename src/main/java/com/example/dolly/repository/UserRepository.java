package com.example.dolly.repository;

import com.example.dolly.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    <T> List<T> findByUsernameNot(String username, Class<T> type);
}
