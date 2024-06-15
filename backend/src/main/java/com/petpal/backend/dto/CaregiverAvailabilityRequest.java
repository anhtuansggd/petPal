package com.petpal.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CaregiverAvailabilityRequest {
    private String frequency;
    private String dayOfWeek;
    private Integer interval;
    private LocalDate startDate;
    private LocalDate endDate;
}