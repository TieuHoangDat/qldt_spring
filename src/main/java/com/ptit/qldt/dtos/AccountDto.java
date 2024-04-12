package com.ptit.qldt.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private int account_id;
    private String fullName, firstName, lastName;
    private String username;
    private String email;
    private String user_id_telegram;
    private Integer role;
}
