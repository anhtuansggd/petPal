package com.petpal.backend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petpal.backend.enums.PetTypeEnum;
import com.petpal.backend.enums.ServiceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Caregiver extends User{
    // JsonManagedReference is to serialized
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "careGiver")
    private List<Contract> contracts;
    @ElementCollection(targetClass = PetTypeEnum.class)
    @Enumerated(EnumType.STRING)
    private List<PetTypeEnum> petTypes;
    private double rating;
    @OneToOne(mappedBy = "caregiver")
    private CaregiverAvailability caregiverAvailability;
    @ElementCollection(targetClass = ServiceTypeEnum.class)
    @Enumerated(EnumType.STRING)
    private List<ServiceTypeEnum> serviceTypes;

    public Caregiver(User user) {
        super(user.getUserId(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getPhone(), user.getLocation(), user.getIsCaregiver(), user.getContracts(), user.getPets(), (List<Authority>) user.getAuthorities());
    }
}
