package com.petpal.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ContractPets {
    @Id
    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contractId;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;


}
