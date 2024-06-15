package com.petpal.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petpal.backend.domain.Caregiver;
import com.petpal.backend.domain.CaregiverAvailability;
import com.petpal.backend.dto.CaregiverAvailabilityRequest;
import com.petpal.backend.service.CaregiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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

}
