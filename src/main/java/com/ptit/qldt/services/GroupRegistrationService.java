package com.ptit.qldt.services;

import com.ptit.qldt.dtos.GroupRegistrationDto;

import java.util.List;

public interface GroupRegistrationService {

    List<GroupRegistrationDto> findgroupRegistration(int accountId);
}
