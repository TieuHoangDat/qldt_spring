package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group) {
        GroupDto groupDto = GroupDto.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .course(group.getCourse())
                .room(group.getRoom())
                .availableSlots(group.getAvailableSlots())
                .maxStudents(group.getMaxStudents())
                .teacher(group.getTeacher())
                .time(group.getTime())
                .build();
        return groupDto;
    }
}
