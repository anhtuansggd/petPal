package com.petpal.backend.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    private long petId;
    private String petType;
    private String petName;
    private int petAge;
    @ManyToOne(optional = false)
    private Account owner;
}
