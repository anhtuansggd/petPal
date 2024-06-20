package com.petpal.backend.repository;

import com.petpal.backend.domain.CaregiverAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaregiverAvailabilityRepository extends JpaRepository<CaregiverAvailability, Long>{
    Optional<CaregiverAvailability> findByCaregiver_UserId(Long userId);
}