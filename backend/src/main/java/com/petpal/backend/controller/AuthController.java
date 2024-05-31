package com.petpal.backend.controller;

import com.petpal.backend.domain.User;
import com.petpal.backend.dto.AuthCredentialsRequest;
import com.petpal.backend.service.UserService;
import com.petpal.backend.utility.CustomPasswordEncoder;
import com.petpal.backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService  userService;
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        //Check if user exist
        Optional<User> existedUser = userService.findByUsername(user.getUsername());
        if(existedUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Account already existed"));
        }

        //Validate request body
        if(user.getUsername() == null || user.getPassword() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid or missing fields"));
        }


        //Save user and success reponse
        String encodedPassword = customPasswordEncoder.getPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User register successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request){
        try{
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );

            User user = (User) authenticate.getPrincipal();
            user.setPassword(null);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(user)
                    )
                    .body(user);
        } catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token,
                                           @AuthenticationPrincipal User user){
        Boolean isTokenValid = jwtUtil.validateToken(token, user);
        return ResponseEntity.ok(isTokenValid);
    }

}