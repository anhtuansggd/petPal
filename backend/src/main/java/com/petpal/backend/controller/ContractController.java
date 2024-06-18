package com.petpal.backend.controller;

import com.petpal.backend.domain.Contract;
import com.petpal.backend.dto.ContractRequest;
import com.petpal.backend.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @PostMapping("/create")
    public ResponseEntity<?> createContract(@RequestBody ContractRequest contractRequest,
                                            @RequestParam Long petOwnerId,
                                            @RequestParam Long caregiverId){
        Contract contract = contractService.createContract(contractRequest, petOwnerId, caregiverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(contract);
    }

    @PatchMapping("/{contractId}/accept")
    public ResponseEntity<?> acceptContract(@PathVariable Long contractId, @RequestParam Long caregiverId) {
        Contract contract = contractService.acceptContract(contractId, caregiverId);
        return ResponseEntity.ok(contract);
    }

    @PatchMapping("/{contractId}/return")
    public ResponseEntity<?> PetReturn(@PathVariable Long contractId) {
        Contract contract = contractService.petReturn(contractId);
        return ResponseEntity.ok(contract);
    }

    @PatchMapping("/{contractId}/confirm-return")
    public ResponseEntity<?> confirmPetReturn(@PathVariable Long contractId) {
        Contract contract = contractService.confirmPetReturn(contractId);
        return ResponseEntity.ok(contract);
    }
}