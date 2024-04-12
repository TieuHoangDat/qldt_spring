package com.ptit.qldt.services;

import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.GroupRegistration;

import java.util.List;

public interface GroupRegistrationService {

    List<GroupRegistrationDto> findgroupRegistration(int accountId);

    void addGroupRegistration(int accountId, String groupId);

    void deleteGroupRegistration(int accountId, String groupId);

    List<GroupRegistrationDto> findGroupByDayOfWeekAndTime(String dayOfWeek, String time);
}
