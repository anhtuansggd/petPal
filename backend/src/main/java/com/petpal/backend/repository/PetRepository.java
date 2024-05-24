package com.petpal.backend.repository;

import com.petpal.backend.domain.Pet;
import com.petpal.backend.domain.User;
import com.petpal.backend.dto.PetRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findById(Long id);
    List<Pet> findByPetOwner(User owner);
}
