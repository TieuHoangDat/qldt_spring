package com.ptit.qldt.dtos;

import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
@Data
@Builder
public class CourseRegistrationDto {
    private Long id;
    private Account account;
    private Course course;
    private String term;
    private LocalDateTime registrationDate;
    private double grade_10, grade_4;
    private String grade_a;
}
