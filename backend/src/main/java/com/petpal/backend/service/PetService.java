package com.petpal.backend.service;

import com.petpal.backend.domain.Pet;
import com.petpal.backend.domain.User;
import com.petpal.backend.dto.PetRegistration;
import com.petpal.backend.repository.PetRepository;
import com.petpal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Pet> findById(Long id){
        return petRepository.findById(id);
    }

    public Pet save(Pet pet){
        return petRepository.save(pet);
    }

    public Pet save(PetRegistration petRegistrationDto){
        Optional<User> petOwner = userRepository.findById(petRegistrationDto.getPetowner_id());
        Pet pet = new Pet();
        pet.setPetAge(petRegistrationDto.getPetAge());
        pet.setPetName(petRegistrationDto.getPetName());
        pet.setPetType(petRegistrationDto.getPetType());
        petOwner.ifPresent(pet::setPetOwner);
        return petRepository.save(pet);
    }

    public List<Pet> findPetsByUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return petRepository.findByPetOwner(user);
    }

    public void delete(Long petId){
        petRepository.deleteById(petId);
    }

    public Pet savePetMainAvatar(Long petId, MultipartFile mainAvatarFile) throws IOException {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        pet.setMainAvatar(mainAvatarFile.getBytes());
        return petRepository.save(pet);
    }

    public Pet savePetAdditionalImages(Long petId, List<MultipartFile> imageFiles) throws IOException {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        List<byte[]> images = new ArrayList<>();
        for (MultipartFile file : imageFiles) {
            images.add(file.getBytes());
        }
        pet.setImages(images);
        return petRepository.save(pet);
    }

    @Transactional
    public List<byte[]> getPetAdditionalImages(Long petId) throws RuntimeException {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        List<byte[]> images = pet.getImages();
        if (images == null || images.isEmpty()) {
            throw new RuntimeException("No additional images found for this pet.");
        }
        return images;
    }
}
