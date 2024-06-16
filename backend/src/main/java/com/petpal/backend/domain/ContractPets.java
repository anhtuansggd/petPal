package com.petpal.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ContractPets {
    @EmbeddedId
    private ContractPetsId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("contractId")
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contractId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("petId")
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;


}
