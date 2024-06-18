package com.petpal.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caregiver_id")
    private Caregiver careGiver;
    // JsonBackReference is to stop loop reference
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petowner_id")
    private User petOwner;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER , mappedBy = "contract")
    private List<ContractPet> contractPets;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String status;
    private String serviceType;
    private boolean petReturnConfirmed;

    public void petReturn() {
        if (careGiver != null && petOwner != null && status.equals("ON_GOING")) {
            status = "PET_RETURNED";
        }
    }

    public void confirmPetReturn() {
        if (careGiver != null && petOwner != null && status.equals("PET_RETURNED")) {
            petReturnConfirmed = true;
            if (petReturnConfirmed) {
                status = "DONE";
            }
        }
    }

}
