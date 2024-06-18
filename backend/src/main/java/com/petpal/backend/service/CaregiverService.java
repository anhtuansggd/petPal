package com.petpal.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.backend.domain.Caregiver;
import com.petpal.backend.domain.CaregiverAvailability;
import com.petpal.backend.domain.DateRange;
import com.petpal.backend.dto.CaregiverAvailabilityRequest;
import com.petpal.backend.enums.PetTypeEnum;
import com.petpal.backend.enums.ServiceTypeEnum;
import com.petpal.backend.repository.CaregiverAvailabilityRepository;
import com.petpal.backend.repository.CaregiverRepository;
import com.petpal.backend.repository.DateRangeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaregiverService {
    @Autowired
    private CaregiverRepository caregiverRepository;
    @Autowired
    private CaregiverAvailabilityRepository caregiverAvailabilityRepository;
    @Autowired
    private DateRangeRepository dateRangeRepository;

    public Optional<Caregiver> findById(Long id){
        Optional<Caregiver> caregiver = caregiverRepository.findById(id);
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

//    public void updateAvailability(Long caregiverId, LocalDate startDate, LocalDate endDate) {
//        Caregiver caregiver = caregiverRepository.findById(caregiverId).orElseThrow(() ->
//                new EntityNotFoundException("Caregiver not found with ID: " + caregiverId));
//        caregiver.getCaregiverAvailability().getBookedDates().add(startDate);
//        caregiver.getCaregiverAvailability().getBookedDates().add(endDate);
//        caregiverRepository.save(caregiver);
//    }

    public void updateAvailability(Long caregiverId, LocalDate startDate, LocalDate endDate) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId).orElseThrow(EntityNotFoundException::new);
        CaregiverAvailability availability = caregiver.getCaregiverAvailability();

        if (availability.getDaysOfWeek() == null || availability.getDaysOfWeek().isEmpty()) {
            throw new IllegalArgumentException("No available days specified for the caregiver.");
        }

        if (!isBookingWithinAvailableDays(availability, startDate, endDate)) {
            throw new IllegalArgumentException("Caregiver not available on requested days");
        }

//        if (availability.getBookedDateRanges() == null) {
//            availability.setBookedDateRanges(new ArrayList<>());
//        }

        addValidDatesToBookedDates(availability, startDate, endDate);
        caregiverAvailabilityRepository.save(availability);
    }

    //Caregiver Availability Logic
    private boolean isBookingWithinAvailableDays(CaregiverAvailability availability, LocalDate startDate, LocalDate endDate) {
        List<DayOfWeek> availableDays = availability.getDaysOfWeek().stream()
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .toList();

        return startDate.datesUntil(endDate.plusDays(1))
                .map(LocalDate::getDayOfWeek)
                .allMatch(availableDays::contains);
    }

    private void addValidDatesToBookedDates(CaregiverAvailability availability, LocalDate startDate, LocalDate endDate) {
        DateRange newBooking = new DateRange(startDate, endDate);
        newBooking.setCaregiverAvailability(availability);
        dateRangeRepository.save(newBooking); // Manually save each DateRange
        availability.getBookedDateRanges().add(newBooking);
        caregiverAvailabilityRepository.save(availability); // Save the availability to update associations
    }



}