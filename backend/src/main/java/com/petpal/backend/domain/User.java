package com.petpal.backend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    @Embedded
    private Location location;
    private int isCaregiver;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petOwner")
    @JsonIgnore
    private List<Contract> contracts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "petOwner")
    @JsonIgnore
    private List<Pet> pets = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Authority> authorities = new ArrayList<>();
    @JsonIgnore
    @Column
    @Lob
    private byte[] avatar;

    public User(Long userId, String username, String password, String name, String email, String phone, Location location, int isCaregiver, List<Contract> contracts, List<Pet> pets, List<Authority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.isCaregiver = isCaregiver;
        this.contracts = contracts;
        this.pets = pets;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    @JsonProperty("authorities")
    public List<String> getAuthoritiesJson(){
        return      authorities.stream()
                .map(Authority::getAuthority)
                .collect(Collectors.toList());
    }
}