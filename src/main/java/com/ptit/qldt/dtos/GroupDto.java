package com.ptit.qldt.dtos;

import lombok.Data;

@Data
public class GroupDto {
    private String group_id;
    private String group_name;
    private String course_id;
    private String time;
    private int teacher_id;
    private String room;
    private int max_students;
    private int available_slots;
}
