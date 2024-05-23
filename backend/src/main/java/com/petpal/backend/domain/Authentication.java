package com.petpal.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Authentication {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String username;
    private String password;


}
