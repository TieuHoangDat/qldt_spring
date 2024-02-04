package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.services.CourseService;
import com.ptit.qldt.services.GroupRegistrationService;
import com.ptit.qldt.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GroupRegistrationController {
    private CourseService courseService;
    private GroupRegistrationService groupRegistrationService;
    private GroupService groupService;
    @Autowired
    public GroupRegistrationController(CourseService courseService, GroupRegistrationService groupRegistrationService, GroupService groupService) {
        this.courseService = courseService;
        this.groupRegistrationService = groupRegistrationService;
        this.groupService = groupService;
    }




    @GetMapping("/group_register")
    public String listCoursesRegister(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("acc");
        List<CourseDto> courses = courseService.findCourseRegister(user.getAccount_id());
        List<GroupRegistrationDto> groupRegistrations = groupRegistrationService.findgroupRegistration(user.getAccount_id());
        List<GroupDto> groups = groupService.findAllGroupInCourseRegistration(user.getAccount_id());
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        model.addAttribute("groupRegistrations", groupRegistrations);
        return "groupRegister";
    }
}
