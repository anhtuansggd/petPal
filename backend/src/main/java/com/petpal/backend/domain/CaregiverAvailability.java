package com.petpal.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;


import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CaregiverAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabitlityId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caregiver_id")
    @JsonIgnore
    private Caregiver caregiver;

    private String frequency;
    private String dayOfWeek;
    private Integer interval;
    private LocalDate startDate;
    private LocalDate endDate;

    /*
    * TODO: When we have a booking system, we will need to keep track of the dates that are booked
    * */
    //@ElementCollection
    //private List<LocalDate> bookedDates = new ArrayList<>();

    @Builder
    public CaregiverAvailability(Caregiver caregiver, String frequency, String dayOfWeek, Integer interval,
                                 LocalDate startDate, LocalDate endDate) {
        this.caregiver = caregiver;
        this.frequency = frequency;
        this.dayOfWeek = dayOfWeek;
        this.interval = interval;

        this.startDate = startDate;
        this.endDate = endDate;
    }

}
