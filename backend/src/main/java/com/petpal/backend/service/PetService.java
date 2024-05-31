package com.petpal.backend.service;

import com.petpal.backend.domain.Pet;
import com.petpal.backend.domain.User;
import com.petpal.backend.dto.PetRegistration;
import com.petpal.backend.repository.PetRepository;
import com.petpal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
