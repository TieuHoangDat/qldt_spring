package com.ptit.qldt.services;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.CourseRegistration;
import com.ptit.qldt.models.Group;

import java.util.List;

public interface GroupService {
    List<GroupDto> findAllGroup();
    List<GroupDto> findAllGroupInCourseRegistration(int accountId);

    List<Group> getGroupByTeacherID(int accountId);

    List<GroupDto> getGroupsForCourse(String courseId);

    Group getGroupById(String groupId);

    Group saveGroup(Group group);

    List<AccountDto> getAccountByCourseId(String courseId);

    GroupDto findGroupById(String groupId);

    void updateGroup(GroupDto group);

    void delete(String groupId);

    List<CourseRegistrationDto> readExcel(String filePath,String courseId);

    CourseRegistrationDto findCourseRegistration(String courseId , int accountId);

    void updateCourseRigistation(CourseRegistrationDto courseRegistrationDto);

    List<AccountDto> findAllAccount();
}
