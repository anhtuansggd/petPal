package com.petpal.backend.service;

import com.petpal.backend.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;
}
