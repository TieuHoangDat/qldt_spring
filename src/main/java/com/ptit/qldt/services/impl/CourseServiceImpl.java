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

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);

    }

    @Override
    public CourseDto findCourseById(String courseId) {
        Course course = courseRepository.findById(courseId).get();
        return mapToCourseDto(course);
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        Course course = mapToCourse(courseDto);
        courseRepository.save(course);
    }

    @Override
    public void delete(String courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<CourseDto> findCourseByName(String name) {
        List<Course> courses = courseRepository.findByName(name);
        return courses.stream().map(course -> mapToCourseDto(course)).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> findCourseBySemester(int semester) {
        List<Course> courses = courseRepository.findBySemester(semester);
        return courses.stream().map(course -> mapToCourseDto(course)).collect(Collectors.toList());
    }

    private Course mapToCourse(CourseDto course) {
        Course courseDto = Course.builder()
                .id(course.getId())
                .name(course.getName())
                .num_credit(course.getNum_credit())
                .term(course.getTerm())
                .notcal(course.getNotcal()).build();
        return courseDto;
    }
}
