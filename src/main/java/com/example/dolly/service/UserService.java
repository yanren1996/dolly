package com.example.dolly.service;

import com.example.dolly.model.vo.UserVo;
import com.example.dolly.model.entity.UserEntity;
import com.example.dolly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 超級無敵偷懶之spring security類別實作
 */
@Service
public class UserService implements UserDetailsManager {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(username);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("user not found:" + username);
        }

        return User.builder()
                .username(userEntity.get().getUsername())
                .password(userEntity.get().getPassword())
                .roles("USER")
                .build();
    }

    @Override
    public void createUser(UserDetails user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        userRepository.save(entity);
    }

    @Override
    public void updateUser(UserDetails user) {
        // 先不做
    }

    @Override
    public void deleteUser(String username) {
        // 先不做
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // 先不做
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    // 以下是一般的Service功能

    public List<UserVo> getUserList(){
        return userRepository.findAllBy(UserVo.class);
    }
}
