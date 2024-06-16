package com.petpal.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //1 account has many roles
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @NonNull
    private String authority;


    @Override
    public String getAuthority() {
        //interface required by Granted Authority
        return authority;
    }
}
