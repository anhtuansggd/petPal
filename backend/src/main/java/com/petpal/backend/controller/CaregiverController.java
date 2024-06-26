package com.petpal.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petpal.backend.domain.Caregiver;
import com.petpal.backend.domain.CaregiverAvailability;
import com.petpal.backend.dto.CaregiverAvailabilityRequest;
import com.petpal.backend.enums.PetTypeEnum;
import com.petpal.backend.enums.ServiceTypeEnum;
import com.petpal.backend.service.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/caregivers")
public class CaregiverController {
    @Autowired
    CaregiverService caregiverService;

    @GetMapping("/{caregiverId}")
    public ResponseEntity<?> getCaregiverProfile(@PathVariable Long caregiverId) {
        Optional<Caregiver> caregiver = caregiverService.findById(caregiverId);
        if (caregiver.isPresent()) {
            caregiver.get().setPassword(null);
            return ResponseEntity.status(HttpStatus.OK).body(caregiver);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Not found Caregiver"));
        }
    }

    @PostMapping("/{caregiverId}/availability")
    public ResponseEntity<?> setCaregiverAvailability(@PathVariable Long caregiverId,
                                                      @RequestBody CaregiverAvailabilityRequest request) {
        try{
            CaregiverAvailability caregiverAvailability = caregiverService.saveAvailability(caregiverId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(caregiverAvailability);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Not found Caregiver"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{caregiverId}/availability")
    public ResponseEntity<?> getCaregiverAvailability(@PathVariable Long caregiverId) {
        Optional<Caregiver> caregiver = caregiverService.findById(caregiverId);
        if (caregiver.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Not found Caregiver"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(caregiver.get().getCaregiverAvailability());
    }

    @PatchMapping("/{caregiverId}/availability")
    public ResponseEntity<?> updateCaregiverAvailability(@PathVariable Long caregiverId,
                                                         @RequestBody CaregiverAvailabilityRequest request) {
        try {
            CaregiverAvailability caregiverAvailability = caregiverService.saveAvailability(caregiverId, request);
            return ResponseEntity.status(HttpStatus.OK).body(caregiverAvailability);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Not found Caregiver"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCaregiver(
            @RequestParam List<PetTypeEnum> petTypes,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam ServiceTypeEnum serviceType) {

        List<Caregiver> caregivers = caregiverService.searchCaregivers(petTypes, startDate, endDate,  serviceType, longitude, latitude);
        for(Caregiver caregiver : caregivers){
            caregiver.setPassword(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(caregivers);
    }

    @PatchMapping("/{caregiverId}/pet-types")
    public ResponseEntity<?> updateCaregiverPetTypes(@PathVariable Long caregiverId, @RequestBody List<PetTypeEnum> petTypes) {
        try {
            Caregiver updatedCaregiver = caregiverService.updatePetTypes(caregiverId, petTypes);
            updatedCaregiver.setPassword(null);
            return ResponseEntity.ok(updatedCaregiver);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Caregiver not found"));
        }
    }

    @PatchMapping("/{caregiverId}/service-types")
    public ResponseEntity<?> updateCaregiverServiceTypes(@PathVariable Long caregiverId, @RequestBody List<ServiceTypeEnum> serviceTypes) {
        try {
            Caregiver updatedCaregiver = caregiverService.updateServiceTypes(caregiverId, serviceTypes);
            updatedCaregiver.setPassword(null);
            return ResponseEntity.ok(updatedCaregiver);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Caregiver not found"));
        }
    }


}
