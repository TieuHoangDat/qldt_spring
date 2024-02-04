package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.repositories.CourseRepository;
import com.ptit.qldt.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.CourseMapper.mapToCourseDto;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDto> findAllCourse() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> mapToCourseDto(course)).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> findCourseRegister(int accountId) {
        List<Course> courses = courseRepository.findCourseRegister(accountId);
        return courses.stream().map(course -> mapToCourseDto(course)).collect(Collectors.toList());
    }


}
