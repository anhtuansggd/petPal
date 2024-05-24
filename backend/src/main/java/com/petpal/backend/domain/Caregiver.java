package com.petpal.backend.domain;

import com.petpal.backend.enums.PetTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Caregiver extends User{
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "careGiver")
    private List<Contract> contracts;
    @ElementCollection(targetClass = PetTypeEnum.class)
    @Enumerated(EnumType.STRING)
    private List<PetTypeEnum> petTypes;
    private double rating;
}
