package com.petpal.backend.domain;

import jakarta.persistence.*;
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;
    private LocalDate startDate;
    private LocalDate endDate;

}
