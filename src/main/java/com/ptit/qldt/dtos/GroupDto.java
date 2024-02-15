package com.ptit.qldt.dtos;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {
    private String groupId;
    private String groupName;
    private Course course;
    private String time;
    private Account teacher;
    private String room;
    private int maxStudents;
    private int availableSlots;
    private boolean registed;
}
