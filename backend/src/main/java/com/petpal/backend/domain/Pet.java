package com.petpal.backend.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long petId;
    private String petType;
    private String petName;
    private int petAge;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petowner_id")
    private User petOwner;
}
