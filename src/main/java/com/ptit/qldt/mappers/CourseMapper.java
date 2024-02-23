package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.services.CourseService;

import java.util.List;

public class CourseMapper {
    public static CourseDto mapToCourseDto(Course course) {
        CourseDto courseDto = CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .num_credit(course.getNum_credit())
                .term(course.getTerm())
                .notcal(course.getNotcal())
                .build();
        return courseDto;
    }

}
