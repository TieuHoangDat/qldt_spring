package com.ptit.qldt.services;

import com.ptit.qldt.dtos.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAllCourse();


    List<CourseDto> findCourseRegister(int accountId);
}
