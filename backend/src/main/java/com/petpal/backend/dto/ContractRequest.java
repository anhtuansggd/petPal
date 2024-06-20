package com.petpal.backend.dto;

import com.petpal.backend.enums.PetTypeEnum;
import com.petpal.backend.enums.ServiceTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ContractRequest {
    private List<Long> petIds;
    private ServiceTypeEnum serviceType;
    private LocalDate startDate;
    private LocalDate endDate;
}