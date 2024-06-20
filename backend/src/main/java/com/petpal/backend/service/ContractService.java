package com.petpal.backend.service;

import com.petpal.backend.domain.*;
import com.petpal.backend.dto.ContractRequest;
import com.petpal.backend.repository.ContractPetRepository;
import com.petpal.backend.repository.ContractRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private CaregiverService caregiverService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContractPetRepository contractPetRepository;

    public ContractPet createContractPet(Contract contract, Pet pet) {
        ContractPetId contractPetId = new ContractPetId(contract.getContractId(), pet.getPetId());

//        contractPetRepository.save(contractPet);
        return new ContractPet(contractPetId, contract, pet);
    }

    public Contract createContract(ContractRequest request, Long petOwnerId, Long caregiverId) {
        Contract contract = new Contract();
        contract.setPetOwner(userService.findByIdFull(petOwnerId).orElseThrow(
                () -> new EntityNotFoundException("User not found with ID: " + petOwnerId)));
        contract.setCareGiver(caregiverService.findById(caregiverId).orElseThrow(
                () -> new EntityNotFoundException("Caregiver not found with ID: " + caregiverId)
        ));
        contract.setServiceType(request.getServiceType().toString());
        contract.setStartDate(request.getStartDate());
        contract.setEndDate(request.getEndDate());
        contract.setStatus("CREATED");

        contract = contractRepository.save(contract);
//        caregiverService.updateAvailability(caregiverId, request.getStartDate(), request.getEndDate());

        Contract finalContract = contract;
        List<ContractPet> contractPetList = request.getPetIds().stream()
                .map(petId -> {
                    Pet pet = petService.findById(petId).orElseThrow(
                            () -> new EntityNotFoundException("Pet not found with ID: " + petId)
                    );
                    ContractPet contractPet = createContractPet(finalContract, pet);
                    contractPetRepository.save(contractPet);
                    return contractPet;
                }).collect(Collectors.toList());

        contract.setContractPets(contractPetList);
        return contract;
    }

    public Contract acceptContract(Long contractId, Long caregiverId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        Caregiver caregiver = caregiverService.findById(caregiverId).orElseThrow();

        contract.setCareGiver(caregiver);
        contract.setStatus("ON_GOING");
        contractRepository.save(contract);

        CaregiverAvailability availability = caregiver.getCaregiverAvailability();
//TODO: 17/6
        //        if(availability.getBookedDates())

        caregiverService.updateAvailability(caregiverId, contract.getStartDate(), contract.getEndDate());

        return contract;
    }

    public Contract rejectContract(Long contractId, Long caregiverId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(
                () -> new EntityNotFoundException("Contract not found with ID: " + contractId)
        );
        contract.setStatus("REJECTED");
        return contractRepository.save(contract);
    }

    public Contract petReturn(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(
                () -> new EntityNotFoundException("Contract not found with ID: " + contractId)
        );
        contract.petReturn();
        return contractRepository.save(contract);
    }

    public Contract confirmPetReturn(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElseThrow(
                () -> new EntityNotFoundException("Contract not found with ID: " + contractId)
        );
        contract.confirmPetReturn();
        return contractRepository.save(contract);
    }

    public Contract updateContractStatus(Long contractId, String newStatus) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with ID: " + contractId));
        contract.setStatus(newStatus);
        return contractRepository.save(contract);
    }


}