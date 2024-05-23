package com.petpal.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CaregiverAvailability {
    @Id
    private Long availabitlityId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;
    private LocalDate startDate;
    private LocalDate endDate;

}
