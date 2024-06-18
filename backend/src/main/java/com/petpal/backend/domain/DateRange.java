package com.petpal.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "caregiver_booked_dates")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class DateRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "availability_id")
    private CaregiverAvailability caregiverAvailability;

}