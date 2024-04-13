package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.repositories.UserRepository;
import com.ptit.qldt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.AccountMapper.mapToAccountDto;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        Account user = new Account();
        user.setName(registrationDto.getName());
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
//        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPassword(registrationDto.getPassword());

//        user.setRole(3);
        userRepository.save(user);
    }

    @Override
    public Account findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Account findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Account findFirstByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Override
    public List<AccountDto> findStudentsByGroupId(String id) {
        List<Account> accounts = userRepository.findStudentsByGroupId(id);
        return accounts.stream().map(account -> mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void updateOtp(int accountId, String otp) {
        userRepository.updateOtp(accountId, otp);
    }

    @Override
    public void updatePassword(int accountId, String newPassword) {
        userRepository.updatePasswordByAccountId(accountId, newPassword);
    }

    @Override
    public void updateUserIdTelegram(int accountId, String userIdTelegram) {
        userRepository.updateUserIdTelegram(accountId,userIdTelegram);
    }
}
