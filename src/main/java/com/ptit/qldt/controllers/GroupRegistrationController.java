package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.services.CourseService;
import com.ptit.qldt.services.GroupRegistrationService;
import com.ptit.qldt.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        for(GroupDto g : groups) {
            boolean ok = false;
            for(GroupRegistrationDto gr : groupRegistrations) {
                if (g.getGroupId().equals(gr.getGroup().getGroupId())) {
                    ok = true;
                    break;
                }
            }
            g.setRegisted(ok);
        }
        String name = "Chọn môn học";
        model.addAttribute("registercourseactive","active");
        model.addAttribute("name", name);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        model.addAttribute("groupRegistrations", groupRegistrations);
        return "group_register";
    }

    @GetMapping("/group_register/{courseId}/groups")
    public String listGroupsForCourse(HttpSession session, @PathVariable String courseId, Model model) {
        Account user = (Account) session.getAttribute("acc");
        List<CourseDto> courses = courseService.findCourseRegister(user.getAccount_id());
        List<GroupDto> groups = groupService.getGroupsForCourse(courseId);
        List<GroupRegistrationDto> groupRegistrations = groupRegistrationService.findgroupRegistration(user.getAccount_id());
        for(GroupDto g : groups) {
            boolean ok = false;
            for(GroupRegistrationDto gr : groupRegistrations) {
                if (g.getGroupId().equals(gr.getGroup().getGroupId())) {
                    ok = true;
                    break;
                }
            }
            g.setRegisted(ok);
        }
        CourseDto course =  courseService.findCourseById(courseId);
        String courseName = course.getName();

        model.addAttribute("registercourseactive","active");
        model.addAttribute("name", courseName);
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        model.addAttribute("groupRegistrations", groupRegistrations);

        return "group_register";
    }

    @GetMapping("/group_register/{groupId}/add")
    public String addGroupRegistration(HttpSession session, @PathVariable String groupId, Model model) {
        Account user = (Account) session.getAttribute("acc");
        List<CourseDto> courses = courseService.findCourseRegister(user.getAccount_id());
        List<GroupRegistrationDto> groupRegistrations = groupRegistrationService.findgroupRegistration(user.getAccount_id());
        List<GroupDto> groups = groupService.findAllGroupInCourseRegistration(user.getAccount_id());

        model.addAttribute("registercourseactive","active");
        model.addAttribute("courses", courses);
        model.addAttribute("groups", groups);
        model.addAttribute("groupRegistrations", groupRegistrations);


        List<GroupRegistrationDto> listgr = groupRegistrationService.findgroupRegistration(user.getAccount_id());
        Group group = groupService.getGroupById(groupId);

        // Kiểm tra để một môn không được đăng ký 2 nhóm
        for(GroupRegistrationDto x : listgr) {
            if(group.getCourse().getId().equals(x.getGroup().getCourse().getId())) {
                model.addAttribute("blockAlert", "block");
                model.addAttribute("mess", "Môn học đã được đăng ký");
                return "group_register";
            }
        }

        // Kiểm tra nhóm còn slot để đăng ký không
        if(group.getAvailableSlots()<=0) {
            model.addAttribute("blockAlert", "block");
            model.addAttribute("mess", "Nhóm này đã hết chỗ");
            return "group_register";
        }

        // Kiểm tra xem có trừng thời khóa biểu không
        for(GroupRegistrationDto x : listgr) {
            if(group.getTime().equals(x.getGroup().getTime())) {
                model.addAttribute("blockAlert", "block");
                model.addAttribute("mess", "Trùng thời gian");
                return "group_register";
            }
        }


        groupRegistrationService.addGroupRegistration(user.getAccount_id(), groupId);
        return "redirect:/group_register";

    }

    @GetMapping("/group_register/{groupId}/delete")
    public String deleteGroupRegistration(HttpSession session, @PathVariable String groupId,Model model) {
        Account user = (Account) session.getAttribute("acc");
        groupRegistrationService.deleteGroupRegistration(user.getAccount_id(), groupId);

        model.addAttribute("registercourseactive","active");
        return "redirect:/group_register";
    }

    @GetMapping("/time_table")
    public String showTimeTable(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("acc");
        Group b[][] = new Group[10][10];

        if(user.getRole()==3) {
            List<GroupRegistrationDto> groupRegistrations = groupRegistrationService.findgroupRegistration(user.getAccount_id());
            for(GroupRegistrationDto o : groupRegistrations) {
                int x = Integer.parseInt(o.getGroup().getTime().substring(11));
                int y = Integer.parseInt(o.getGroup().getTime().substring(4, 5));
                b[x][y] = o.getGroup();
            }
        } else if(user.getRole()==2){
            List<Group> listg = groupService.getGroupByTeacherID(user.getAccount_id());
            for(Group o : listg) {
                int x = Integer.parseInt(o.getTime().substring(11));
                int y = Integer.parseInt(o.getTime().substring(4, 5));
                b[x][y] = o;
            }
        }
        model.addAttribute("b", b);
        model.addAttribute("tkbactive","active");
        return "time_table";
    }
}
