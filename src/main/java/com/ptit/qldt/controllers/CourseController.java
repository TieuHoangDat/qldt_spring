package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        Course course = new Course();
        String a = "Tất cả";
        List<CourseDto> courses = courseService.findAllCourse();
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("term", a);
        model.addAttribute("courseactive","active");
//        model.addAttribute("notaction","true");
        return "course_manager";
    }

    @PostMapping("/filter")
    public String filterCourseBySemester(Model model,@RequestParam(value = "semester") String semester){
        Course course = new Course();
        List<CourseDto> courses = new ArrayList<>();
        if(semester.equals("Tất cả")){
            courses = courseService.findAllCourse();
        }else {
            courses = courseService.findCourseBySemester(Integer.parseInt(semester));
        }
        model.addAttribute("semester" , semester);
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

    @GetMapping("/courses/term-{term}")
    public String filterCourse(@PathVariable("term") String term,Model model){
        Course course = new Course();
        List<CourseDto> courses = new ArrayList<>();
        if(term.equals("Tất cả")){
            courses = courseService.findAllCourse();
        }else {
            courses = courseService.findCourseBySemester(Integer.parseInt(term));
        }
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }
    @GetMapping("/courses/new")
    public String createCourse(Model model){
        Course course = new Course();
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

    @PostMapping("/courses/new")
    public String saveCourse(@ModelAttribute("course") Course course,Model model){
        courseService.saveCourse(course);
        model.addAttribute("courseactive","active");
        return "redirect:/courses";
    }

    @GetMapping("/courses/{courseId}/edit")
    public String editCourse(@PathVariable("courseId") String courseId,Model model){
        CourseDto course =  courseService.findCourseById(courseId);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
//        model.addAttribute("displayEdit","block");
        return "edit_course";
    }

    @PostMapping("/courses/{courseId}/edit")
    public String updateCourse(Model model,@PathVariable("courseId") String courseId, @ModelAttribute("course") CourseDto course
                               ){
        course.setId(courseId);
        courseService.updateCourse(course);
        model.addAttribute("courseactive","active");
        return "redirect:/courses";
    }

    @GetMapping("/courses/{courseId}/delete")
    public String deleteCourse(@PathVariable("courseId") String courseId,Model model){
        try {
            courseService.delete(courseId);
            return "redirect:/courses";
        }catch (Exception ex){
            System.out.println("không xóa được");
//            model.addAttribute("notaction","block");
        }
        Course course = new Course();
        String a = "Tất cả";
        List<CourseDto> courses = courseService.findAllCourse();
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("term", a);
        model.addAttribute("notaction","block");
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

    @GetMapping("/search")
    public String listCourseSearch(@RequestParam(value = "searchCourse") String name , Model model){
        List<CourseDto> courses = courseService.findCourseByName(name);
        Course course = new Course();
        model.addAttribute("courses",courses);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

}
