package com.petpal.backend.service;

import com.petpal.backend.domain.User;
import com.petpal.backend.repository.UserRepository;
import com.petpal.backend.utility.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User save(User user){

        return userRepository.save(user);
    }






}


