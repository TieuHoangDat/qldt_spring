package com.ptit.qldt.models;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @Column(name = "course_id")
    private String id;

    @Column(name = "course_name", nullable = false)
    private String name;

    @Column(name = "num_credit", nullable = false)
    private int num_credit;

    @Column(nullable = false)
    private int term;

    @Column(name = "notcal", columnDefinition = "BIT DEFAULT 0 NOT NULL")
    private int notcal;
}
