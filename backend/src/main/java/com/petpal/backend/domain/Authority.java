package com.petpal.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@NoArgsConstructor
/*
    Jpa requires non-args-constructor for instantiate class
 */
@RequiredArgsConstructor
/*
    use for create constructor with @NonNull
 */
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Many roles to 1 account
    @ManyToOne(optional = false)
    private User user;
    @NonNull
    private String authority;


    @Override
    public String getAuthority() {
        //interface required by Granted Authority
        return authority;
    }
}
