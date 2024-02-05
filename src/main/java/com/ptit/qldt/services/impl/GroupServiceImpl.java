package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.repositories.CourseRepository;
import com.ptit.qldt.repositories.GroupRepository;
import com.ptit.qldt.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.GroupMapper.mapToGroupDto;

@Service
public class GroupServiceImpl implements GroupService {
    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    @Autowired
    public GroupServiceImpl(CourseRepository courseRepository, GroupRepository groupRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
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
    public Group getGroupById(String groupId) {
        return groupRepository.findGroupById(groupId);
    }
}
