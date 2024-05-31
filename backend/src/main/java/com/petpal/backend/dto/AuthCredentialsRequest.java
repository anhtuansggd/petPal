package com.petpal.backend.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AuthCredentialsRequest {
    private String username;
    private String password;
}
