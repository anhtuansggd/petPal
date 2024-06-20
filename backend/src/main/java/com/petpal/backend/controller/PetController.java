package com.petpal.backend.controller;

import com.petpal.backend.domain.Pet;
import com.petpal.backend.dto.PetRegistration;
import com.petpal.backend.repository.PetRepository;
import com.petpal.backend.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @PostMapping("/{petId}/avatar")
    public ResponseEntity<?> uploadPetMainAvatar(@PathVariable Long petId, @RequestParam("avatar") MultipartFile mainAvatarFile) throws IOException {
        Pet pet = petService.savePetMainAvatar(petId, mainAvatarFile);
        return ResponseEntity.ok(Map.of("message", "Avatar uploaded successfully", "petId", pet.getPetId()));
    }

    @PostMapping("/{petId}/additional-images")
    public ResponseEntity<?> uploadPetAdditionalImages(@PathVariable Long petId, @RequestParam("images") List<MultipartFile> imageFiles) throws IOException {
        Pet pet = petService.savePetAdditionalImages(petId, imageFiles);
        return ResponseEntity.ok(Map.of("message", "Additional images uploaded successfully", "petId", pet.getPetId()));
    }

    @GetMapping("/{petId}/avatar")
    public ResponseEntity<byte[]> getPetMainAvatar(@PathVariable Long petId) {
        Pet pet = petService.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        byte[] avatar = pet.getMainAvatar();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(avatar, headers, HttpStatus.OK);
    }

    @GetMapping("/{petId}/additional-images")
    public ResponseEntity<?> getPetAdditionalImages(@PathVariable Long petId) {
        try {
            List<byte[]> images = petService.getPetAdditionalImages(petId);
            List<String> base64Images = images.stream()
                    .map(image -> Base64.getEncoder().encodeToString(image))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(base64Images);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }
}
