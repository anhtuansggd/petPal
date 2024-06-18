package com.petpal.backend.controller;

import com.petpal.backend.domain.Location;
import com.petpal.backend.domain.Pet;
import com.petpal.backend.domain.User;
import com.petpal.backend.repository.UserRepository;
import com.petpal.backend.service.PetService;
import com.petpal.backend.service.UserService;
import com.petpal.backend.utility.CustomPasswordEncoder;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;
    @Autowired
    private PetService petService;

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfile(@PathVariable String username){
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Not found User"));
        }
    }


    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> updates, @RequestParam String username){
        Optional<User> userOptional = userService.findByUserNameFull(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Location location = user.getLocation() != null ? user.getLocation() : new Location();
            updates.forEach((update, value) -> {
                switch (update) {
                    case "username":
                        user.setUsername((String) value);
                        break;
                    case "password":
                        String newPassword = (String) value;
                        String encodedPassword = customPasswordEncoder.getPasswordEncoder().encode(newPassword);
                        user.setPassword(encodedPassword);
                        break;
                    case "name":
                        user.setName((String) value);
                        break;
                    case "email":
                        user.setEmail((String) value);
                        break;
                    case "phone":
                        user.setPhone((String) value);
                        break;
                    case "isCaregiver":
                        //TODO: the case where caregiver decide to stop
                        user.setIsCaregiver((Integer) value);
                    case "longitude":
                        location.setLongitude(Double.parseDouble(value.toString()));
                        break;
                    case "latitude":
                        location.setLatitude(Double.parseDouble(value.toString()));
                        break;

                    default:
                        break;
                }
            });
            user.setLocation(location);
            userService.save(user);
            user.setPassword(null);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
    }

    @GetMapping("/{username}/pets")
    public ResponseEntity<?> getPetsByUSer(@PathVariable String username){
        try {
            List<Pet> pets = petService.findPetsByUser(username);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
        }
    }

    @PostMapping("/profile/{userId}/avatar")
    public ResponseEntity<?> uploadUserAvatar(@PathVariable Long userId, @RequestParam("avatar") MultipartFile avatarFile) throws IOException {
        User user = userService.saveUserWithAvatar(userId, avatarFile);
        return ResponseEntity.ok(Map.of("message", "Avatar uploaded successfully", "userId", user.getUserId()));
    }
}
