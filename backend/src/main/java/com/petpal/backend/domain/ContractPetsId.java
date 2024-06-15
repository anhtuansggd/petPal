package com.petpal.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ContractPetsId implements Serializable {
    private Long contractId;
    private Long petId;
}
