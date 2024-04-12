package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.models.GroupRegistration;
import com.ptit.qldt.repositories.GroupRegistrationRepository;
import com.ptit.qldt.repositories.GroupRepository;
import com.ptit.qldt.services.GroupRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.GroupMapper.mapToGroupDto;
import static com.ptit.qldt.mappers.GroupRegistrationMapper.mapToGroupRegistrationDto;

@Service
public class GroupRegistrationServiceImpl implements GroupRegistrationService {
    private GroupRegistrationRepository groupRegistrationRepository;
    private GroupRepository groupRepository;
    @Autowired
    public GroupRegistrationServiceImpl(GroupRegistrationRepository groupRegistrationRepository, GroupRepository groupRepository) {
        this.groupRegistrationRepository = groupRegistrationRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupRegistrationDto> findgroupRegistration(int accountId) {
        List<GroupRegistration> groupRegistrations = groupRegistrationRepository.findGroupRegistration(accountId);
        return groupRegistrations.stream().map(groupRegistration -> mapToGroupRegistrationDto(groupRegistration)).collect(Collectors.toList());
//        return null;
    }

    @Override
    public void addGroupRegistration(int accountId, String groupId) {
        Account account = new Account();
        account.setAccount_id(accountId);

        Group group = new Group();
        group.setGroupId(groupId);

        GroupRegistration groupRegistration = new GroupRegistration();
        groupRegistration.setAccount(account);
        groupRegistration.setGroup(group);

        // Lưu đối tượng GroupRegistration vào cơ sở dữ liệu
        groupRegistrationRepository.save(groupRegistration);
        groupRepository.decreaseAvailableSlots(groupId);
    }

    @Override
    public void deleteGroupRegistration(int accountId, String groupId) {
        groupRegistrationRepository.deleteByAccountIdAndGroupId(accountId, groupId);
        groupRepository.increaseAvailableSlots(groupId);
    }

    @Override
    public List<GroupRegistrationDto> findGroupByDayOfWeekAndTime(String dayOfWeek, String time) {
        List<GroupRegistration> groupRegistrations = groupRegistrationRepository.findGroupRegistrationByDayOfWeekandTime(dayOfWeek,time);
        return groupRegistrations.stream().map(groupRegistration -> mapToGroupRegistrationDto(groupRegistration)).collect(Collectors.toList());
    }


}
