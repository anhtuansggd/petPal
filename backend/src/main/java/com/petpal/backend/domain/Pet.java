package com.petpal.backend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long petId;
    private String petType;
    private String petName;
    private int petAge;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petowner_id")
    private User petOwner;
    @JsonIgnore
    @Column
    @Lob
    private byte[] mainAvatar;
    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "pet_images", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "image")
    @Lob
    private List<byte[]> images = new ArrayList<>();
}
