package com.petpal.backend.controller;

import com.petpal.backend.domain.Pet;
import com.petpal.backend.dto.PetRegistration;
import com.petpal.backend.repository.PetRepository;
import com.petpal.backend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPet(@RequestBody PetRegistration petRegistration){
        Pet savedPet = petService.save(petRegistration);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Pet's id: "+ savedPet.getPetId()));
    }


    @PatchMapping("/{petId}")
    public ResponseEntity<?> updatePet(@RequestBody Map<String, Object> updates, @PathVariable Long petId){
        Optional<Pet> petOptional = petService.findById(petId);
        if(petOptional.isPresent()){
            Pet pet = petOptional.get();
            updates.forEach((update, value) -> {
                switch (update) {
                    case "petType":
                        pet.setPetType((String) value);
                        break;
                    case "petName":
                        pet.setPetName((String) value);
                        break;
                    case "petAge":
                        pet.setPetAge((Integer) value);
                        break;
                    default:
                        break;
                }
            });
            petService.save(pet);
            return ResponseEntity.status(HttpStatus.OK).body(pet);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Pet not found"));
        }
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId){
        petService.delete(petId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}