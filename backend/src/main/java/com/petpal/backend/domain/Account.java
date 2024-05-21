package com.petpal.backend.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String location;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private List<Pet> pets = new ArrayList<>();
}
