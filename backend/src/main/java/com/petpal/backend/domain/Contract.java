package com.petpal.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long contractId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private Caregiver careGiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petowner_id")
    private User petOwner;
    @OneToMany(fetch = FetchType.LAZY , mappedBy = "contractId")
    private List<ContractPets> contractPets;
    private LocalDate startDate;
    private LocalDate returnDate;
    private double price;
    private String status;

}
