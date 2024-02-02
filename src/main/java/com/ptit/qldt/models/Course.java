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
    @Column(name = "course_name")
    private String name;
    private int num_credit;
    private int term;
    private int notcal;


}
