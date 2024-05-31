package com.petpal.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRegistration {
    private String petType;
    private String petName;
    private int petAge;
    private Long petowner_id;

}
