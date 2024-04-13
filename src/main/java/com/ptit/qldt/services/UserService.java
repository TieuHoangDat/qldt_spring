package com.ptit.qldt.services;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;

import java.util.List;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    Account findByEmail(String email);

    Account findByUsername(String username);

    Account findFirstByUsername(String username);

    List<AccountDto> findStudentsByGroupId(String id);

    void updateOtp(int accountId, String otp);

    void updatePassword(int accountId, String newPassword);
    void updateUserIdTelegram(int accountId , String userIdTelegram);
}
