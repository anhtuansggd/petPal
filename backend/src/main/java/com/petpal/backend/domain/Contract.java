package com.petpal.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Contract {
    @Id
    private Long contractId;
    private Long caregiverId;
    private Long petownerId;
    private LocalDate startDate;
    private LocalDate returnDate;
    private double price;
    private String status;

}
