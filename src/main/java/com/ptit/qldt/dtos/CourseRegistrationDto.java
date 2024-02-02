package com.ptit.qldt.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;
@Data
public class CourseRegistrationDto {
    private AccountDto acccount;
    private CourseDto course;
    private String term;
    private Date time;
    private double grade_10, grade_4;
    private String grade_alpha;
}
