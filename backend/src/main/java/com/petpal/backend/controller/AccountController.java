package com.petpal.backend.controller;
import com.petpal.backend.dto.AccountDto;
import com.petpal.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
//    @Autowired
//    private AccountService accountService;
//
//    @PostMapping("")
//    public ResponseEntity<?> createAccount(@RequestBody AccountDto newAccountDto){
//        return null;
//    }
//
//    @GetMapping("")
//    public ResponseEntity<?> getAccount(@RequestParam String username){
//        return null;
//    }
//
//    @DeleteMapping("")
//    public ResponseEntity<?> deleteAccount(@RequestParam String username){
//        try {
//            return ResponseEntity.ok("Account Deleted");
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


}
