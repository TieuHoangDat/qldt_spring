package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<CourseDto> courses = courseService.findAllCourse();
        model.addAttribute("courses", courses);
        return "course_manager";
    }
}
