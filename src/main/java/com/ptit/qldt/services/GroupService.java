package com.ptit.qldt.services;

import com.ptit.qldt.dtos.GroupDto;

import java.util.List;

public interface GroupService {
    List<GroupDto> findAllGroupInCourseRegistration(int accountId);
}
