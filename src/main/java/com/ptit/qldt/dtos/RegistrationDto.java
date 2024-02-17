package com.ptit.qldt.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class RegistrationDto {
    private int account_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String otp;
}
