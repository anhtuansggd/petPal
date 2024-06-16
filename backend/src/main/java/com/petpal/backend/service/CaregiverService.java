package com.petpal.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.backend.domain.Caregiver;
import com.petpal.backend.domain.CaregiverAvailability;
import com.petpal.backend.dto.CaregiverAvailabilityRequest;
import com.petpal.backend.enums.PetTypeEnum;
import com.petpal.backend.enums.ServiceTypeEnum;
import com.petpal.backend.repository.CaregiverAvailabilityRepository;
import com.petpal.backend.repository.CaregiverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CaregiverService {
    @Autowired
    CaregiverRepository caregiverRepository;
    @Autowired
    CaregiverAvailabilityRepository caregiverAvailabilityRepository;

    public Optional<Caregiver> findById(Long id){
        Optional<Caregiver> caregiver = caregiverRepository.findById(id);
        caregiver.ifPresent(value -> value.setPassword(null));
        return caregiver;
    }

    public CaregiverAvailability saveAvailability(Long caregiverId, CaregiverAvailabilityRequest request) throws JsonProcessingException {
        Optional<Caregiver> caregiver = findById(caregiverId);
        if(caregiver.isEmpty()){
            throw new EntityNotFoundException();
        }
        Optional<CaregiverAvailability> existingAvailability = caregiverAvailabilityRepository.findByCaregiver_UserId(caregiverId);
        if(existingAvailability.isPresent()){
            CaregiverAvailability availability = existingAvailability.get();

            if(request.getFrequency() != null){
                availability.setFrequency(request.getFrequency());
            }
            if(request.getDaysOfWeek() != null){
                availability.setDaysOfWeek(request.getDaysOfWeek());
            }
            if(request.getInterval() != null){
                availability.setInterval(request.getInterval());
            }
            if(request.getStartDate() != null){
                availability.setStartDate(request.getStartDate());
            }
            if(request.getEndDate() != null){
                availability.setEndDate(request.getEndDate());
            }


            return caregiverAvailabilityRepository.save(availability);
        }else{
            CaregiverAvailability caregiverAvailability = CaregiverAvailability.builder()
                    .caregiver(caregiver.get())
                    .frequency(request.getFrequency())
                    .daysOfWeek(request.getDaysOfWeek())
                    .interval(request.getInterval())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .build();

            return caregiverAvailabilityRepository.save(caregiverAvailability);
        }

    }

    public Caregiver save(Caregiver caregiver){
        return caregiverRepository.save(caregiver);
    }

    public List<Caregiver> searchCaregivers(List<PetTypeEnum> petTypes, LocalDate startDate, LocalDate endDate, ServiceTypeEnum serviceType, double longitude, double latitude) {
        double radiusInMeters = 15000; // 15km
        return caregiverRepository.searchCaregivers(petTypes, startDate, endDate,  serviceType, longitude, latitude, radiusInMeters);
    }

    public Caregiver updatePetTypes(Long caregiverId, List<PetTypeEnum> petTypes) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(EntityNotFoundException::new);
        caregiver.setPetTypes(petTypes);
        return caregiverRepository.save(caregiver);
    }

    public Caregiver updateServiceTypes(Long caregiverId, List<ServiceTypeEnum> serviceTypes) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(EntityNotFoundException::new);
        caregiver.setServiceTypes(serviceTypes);
        return caregiverRepository.save(caregiver);
    }

}