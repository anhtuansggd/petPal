package com.petpal.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AccountDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String location;

}
