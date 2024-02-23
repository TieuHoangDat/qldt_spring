package com.ptit.qldt.models;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "`Groups`")
public class Group {
    @Id
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String time;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Account teacher;

    @Column(nullable = false)
    private String room;

    @Column(name = "max_students", nullable = false)
    private int maxStudents;

    @Column(name = "available_slots", nullable = false)
    private int availableSlots;
}
