package com.ptit.qldt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courseregistrations")
public class CourseRegistration {
    @Id
    private int id;
    private int account_id;
    private String course_id;
    private String term;
    private Date registration_date;
    private double grade;
}
