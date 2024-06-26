package com.petpal.backend.service;

import com.petpal.backend.domain.User;
import com.petpal.backend.repository.UserRepository;
import com.petpal.backend.utility.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    public Optional<User> findByUsername(String username){
        //nullify password
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> value.setPassword(null));
        return user;
    }

    public Optional<User> findByUserNameFull(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setPassword(null));
        return user;
    }

    public Optional<User> findByIdFull(Long id){
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public User save(User user){

        return userRepository.save(user);
    }

    public User saveUserWithAvatar(Long userId, MultipartFile avatarFile) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAvatar(avatarFile.getBytes());
        return userRepository.save(user);
    }




}


