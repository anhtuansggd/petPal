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
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long userId;
    @OneToOne(mappedBy = "user")
    private Authentication authentication;
    private String name;
    private String email;
    private String phone;
    private String location;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petOwner")
    private List<Contract> contracts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petOwner")
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();


}
