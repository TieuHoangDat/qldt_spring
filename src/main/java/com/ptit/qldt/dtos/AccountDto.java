package com.ptit.qldt.dtos;

import lombok.Data;

@Data
public class AccountDto {
    private int account_id;
    private String name;
    private String username;
    private String email;
    private Integer role;
}
