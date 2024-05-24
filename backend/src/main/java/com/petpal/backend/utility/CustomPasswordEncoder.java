package com.petpal.backend.utility;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class CustomPasswordEncoder {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

}
