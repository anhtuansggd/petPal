package com.petpal.backend.repository;

import com.petpal.backend.domain.Caregiver;
import com.petpal.backend.enums.PetTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    @Query("SELECT c FROM Caregiver c WHERE EXISTS (" +
            "SELECT 1 FROM c.petTypes p WHERE p IN :petTypes) " +
            "AND c.caregiverAvailability.startDate <= :startDate " +
            "AND c.caregiverAvailability.endDate >= :endDate " +
            "AND ST_DistanceSphere(ST_MakePoint(c.location.longitude, c.location.latitude), ST_MakePoint(:longitude, :latitude)) <= :radius")
    List<Caregiver> searchCaregivers(@Param("petTypes") List<PetTypeEnum> petTypes,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     @Param("longitude") double longitude,
                                     @Param("latitude") double latitude,
                                     @Param("radius") double radius);
}
