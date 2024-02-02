package com.ptit.qldt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class Group {
    @Id
    private String group_id;
    private String group_name;
    private String course_id;
    private String time;
    private int teacher_id;
    private String room;
    private int max_students;
    private int available_slots;
}
