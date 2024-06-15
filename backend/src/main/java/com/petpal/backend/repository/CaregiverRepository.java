package com.petpal.backend.repository;

import com.petpal.backend.domain.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
}
