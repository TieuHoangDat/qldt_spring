package com.ptit.qldt.services;

import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    Account findByEmail(String email);

    Account findByUsername(String username);

    Account findFirstByUsername(String username);
}
