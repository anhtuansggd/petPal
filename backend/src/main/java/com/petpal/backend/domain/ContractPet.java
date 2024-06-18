package com.petpal.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ContractPet {
    @EmbeddedId
    private ContractPetId id;


    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("contractId")
    @JoinColumn(name = "contract_id", nullable = false)
    @JsonBackReference
    private Contract contract;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId("petId")
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;


}
