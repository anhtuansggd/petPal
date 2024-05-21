package com.petpal.backend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petpal.backend.dto.AccountDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String location;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();


    public Account(AccountDto newAccountDto) {
        this.username = newAccountDto.getUsername();
        this.password = newAccountDto.getPassword();
        this.email = newAccountDto.getEmail();
        this.phone = newAccountDto.getPhone();
        this.location = newAccountDto.getLocation();
    }
}
