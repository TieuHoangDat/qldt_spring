package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.CourseRegistration;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.repositories.AccountRepository;
import com.ptit.qldt.repositories.CourseRegistrationRepository;
import com.ptit.qldt.repositories.CourseRepository;
import com.ptit.qldt.repositories.GroupRepository;
import com.ptit.qldt.services.GroupService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.AccountMapper.mapToAccountDto;
import static com.ptit.qldt.mappers.CourseMapper.mapToCourseDto;
import static com.ptit.qldt.mappers.CourseRegistrationMapper.mapToCourseRegistrationDto;
import static com.ptit.qldt.mappers.GroupMapper.mapToGroupDto;

@Service
public class GroupServiceImpl implements GroupService {
    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private CourseRegistrationRepository courseRegistrationRepository;
    private AccountRepository accountRepository;
    private GroupService groupService;
    @Autowired
    public GroupServiceImpl(CourseRepository courseRepository,
                            GroupRepository groupRepository,
                            CourseRegistrationRepository courseRegistrationRepository,
                            AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.accountRepository = accountRepository;

    }
    @Override
    public List<GroupDto> findAllGroupInCourseRegistration(int accountId) {
        List<Course> courses = courseRepository.findCourseRegister(accountId);
        List<Group> groups = groupRepository.findGroupsByCourses(courses);
        return groups.stream().map(group -> mapToGroupDto(group)).collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupByTeacherID(int accountId) {
        return groupRepository.getGroupByTeacherID(accountId);
    }

    @Override
    public List<GroupDto> getGroupsForCourse(String courseId) {
        List<Group> groups = groupRepository.findByCourseId(courseId);
        return groups.stream().map(group -> mapToGroupDto(group)).collect(Collectors.toList());
    }

    @Override
    public List<GroupDto> findAllGroup() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(group -> mapToGroupDto(group)).collect(Collectors.toList());
    }
    @Override
    public Group getGroupById(String groupId) {
        return groupRepository.findGroupById(groupId);
    }

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<AccountDto> getAccountByCourseId(String courseId) {
        return null;
    }

    @Override
    public GroupDto findGroupById(String groupId) {
        Group group = groupRepository.findById(groupId).get();
        return mapToGroupDto(group);
    }

    @Override
    public void updateGroup(GroupDto groupDto) {
        Group group = mapToGroup(groupDto);
        groupRepository.save(group);
    }

    @Override
    public void delete(String groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public List<CourseRegistrationDto> readExcel(String filePath, String courseId) {
        return null;
    }

    @Override
    public CourseRegistrationDto findCourseRegistration(String courseId, int accountId) {
        CourseRegistration courseRegistration = courseRepository.findCourseRegisterByCourseIdAndAccountId(courseId,accountId);
        return mapToCourseRegistrationDto(courseRegistration);
    }

    @Override
    public void updateCourseRigistation(CourseRegistrationDto courseRegistrationDto) {
        CourseRegistration courseRegistration = mapToCourseRegistration(courseRegistrationDto);
        courseRegistrationRepository.save(courseRegistration);
    }

    @Override
    public List<AccountDto> findAllTeacherAccount() {
        List<Account> accounts = accountRepository.findAllTeacher();
        return accounts.stream().map(account -> mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> findAllStudentAccount() {
        List<Account> accounts = accountRepository.findAllStudent();
        return accounts.stream().map(account -> mapToAccountDto(account)).collect(Collectors.toList());
    }

    private CourseRegistration mapToCourseRegistration(CourseRegistrationDto courseRegistrationDto){
        CourseRegistration courseRegistration = CourseRegistration.builder()
                .id(courseRegistrationDto.getId())
                .account(courseRegistrationDto.getAccount())
                .course(courseRegistrationDto.getCourse())
                .term(courseRegistrationDto.getTerm())
                .registrationDate(courseRegistrationDto.getRegistrationDate())
                .grade(courseRegistrationDto.getGrade_10()).build();
        return courseRegistration;
    }
    private Group mapToGroup(GroupDto groupDto) {
        Group group = Group.builder().groupId(groupDto.getGroupId())
                .groupName(groupDto.getGroupName())
                .course(groupDto.getCourse())
                .time(groupDto.getTime())
                .teacher(groupDto.getTeacher())
                .room(groupDto.getRoom())
                .maxStudents(groupDto.getMaxStudents())
                .availableSlots(groupDto.getAvailableSlots()).build();
        return group;
    }


}
