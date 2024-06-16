package com.petpal.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CaregiverAvailabilityRequest {
    private String frequency;
    private List<String> daysOfWeek;
    private Integer interval;
    private LocalDate startDate;
    private LocalDate endDate;
}