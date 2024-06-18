package com.petpal.backend.repository;

import com.petpal.backend.domain.ContractPet;
import com.petpal.backend.domain.ContractPetId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractPetRepository extends JpaRepository<ContractPet, ContractPetId> {


}
