package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.models.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account) {
        String fullName = account.getName();
        String[] ss = fullName.split("\\s+");
        int l = ss.length;
        String firstName = ss[l-1];
        StringBuilder lastName = new StringBuilder();
        for(int i=0; i<l-2; i++) {
            lastName.append(ss[i]).append(" ");
        }
        lastName.append(ss[l - 2]);
        AccountDto accountDto = AccountDto.builder()
                .account_id(account.getAccount_id())
                .fullName(account.getName())
                .firstName(firstName)
                .lastName(lastName.toString())
                .username(account.getUsername())
                .email(account.getEmail())
                .user_id_telegram(account.getUser_id_telegram())
                .role(account.getRole())
                .build();
        return accountDto;
    }
}
