package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.GroupRegistration;
import com.ptit.qldt.repositories.GroupRegistrationRepository;
import com.ptit.qldt.services.GroupRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.GroupRegistrationMapper.mapToGroupRegistrationDto;

@Service
public class GroupRegistrationServiceImpl implements GroupRegistrationService {
    private GroupRegistrationRepository groupRegistrationRepository;
    @Autowired
    public GroupRegistrationServiceImpl(GroupRegistrationRepository groupRegistrationRepository) {
        this.groupRegistrationRepository = groupRegistrationRepository;
    }

    @Override
    public List<GroupRegistrationDto> findgroupRegistration(int accountId) {
        List<GroupRegistration> groupRegistrations = groupRegistrationRepository.findGroupRegistration(accountId);
        return groupRegistrations.stream().map(groupRegistration -> mapToGroupRegistrationDto(groupRegistration)).collect(Collectors.toList());
//        return null;
    }
}
