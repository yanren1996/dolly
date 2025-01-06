package com.example.dolly.repository;

import com.example.dolly.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    /**
     * 可以傳入自己想要的VO
     */
    <T> List<T> findAllBy(Class<T> type);
}
