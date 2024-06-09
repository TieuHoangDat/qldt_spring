package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.services.GroupService;
import com.ptit.qldt.services.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Controller
public class GroupController {
    private GroupService groupService;
    private UserService userService;
    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }


    @GetMapping("/groupByCourse/{courseId}")
    public String showGroupByCourse(@PathVariable String courseId , Model model){
        List<GroupDto> groups = groupService.getGroupsForCourse(courseId);
        List<AccountDto> accounts = groupService.findAllTeacherAccount();
        for(AccountDto accountDto : accounts){
            System.out.println(accountDto.getFullName());
        }
        Group group = new Group();

        model.addAttribute("accounts",accounts);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groups", groups);
        model.addAttribute("group",group);
        model.addAttribute("courseactive","active");
        return "group_manager";
    }



    @GetMapping("/group_teacher")
    public String showTimeTable(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("acc");
        List<Group> listg = groupService.getGroupByTeacherID(user.getAccount_id());

        long sonhom = listg.size();

        // Ngày bắt đầu kỳ
        LocalDate start = LocalDate.of(2024, 2, 5);
        // Ngày hiện tại
        LocalDate now = LocalDate.now();
        // Ngày kết thúc
        LocalDate end = LocalDate.of(2024, 5, 26);
        // Số ngày từ fromDate đến toDate
        long sotuandaday = ChronoUnit.WEEKS.between(start, now);
        long sotuanchuaday = ChronoUnit.WEEKS.between(now, end) - 1;
        long daday = sotuandaday*sonhom*2;
        long trongtuan = sonhom*2;
        long chuaday = sotuanchuaday*sonhom*2;
        if(chuaday <= 0){
            daday = ChronoUnit.WEEKS.between(start, end)*sonhom*2;
            trongtuan = 0;
            chuaday = 0;
        }
        long[] data = {daday, trongtuan, chuaday};
        model.addAttribute("chartData", data);
        model.addAttribute("groups", listg);
        model.addAttribute("groupteacheractive","active");
        return "group_teacher";
    }

    @GetMapping("/groupss")
    public String showGroup(Model model , @ModelAttribute("blockDuplicate") String blockDuplicate){
        List<GroupDto> groups = groupService.findAllGroup();
        List<AccountDto> accounts = groupService.findAllTeacherAccount();
        for(GroupDto x : groups){
            System.out.println(x.getGroupId());
        }
        Group group = new Group();
//        model.addAttribute("blockDuplicate","block");
        model.addAttribute("accounts",accounts);
        model.addAttribute("groups", groups);
        model.addAttribute("group",group);
        model.addAttribute("courseactive","active");
        return "group_manager";
    }

    @GetMapping("/groupByCourse/{courseId}/new")
    public String createGroup(@PathVariable(value = "courseId") String courseId ,Model model){
        List<AccountDto> accounts = groupService.findAllTeacherAccount();
        Group group = new Group();
        model.addAttribute("accounts",accounts);
        model.addAttribute("courseId",courseId);
        model.addAttribute("group",group);
        model.addAttribute("courseactive","active");
        return "redirect:/groupByCourse/{courseId}";
    }

    @PostMapping("/groupByCourse/{courseId}/new")
    public String saveGroup(@ModelAttribute("group") Group group ,
                            Model model,
                            @PathVariable(value = "courseId") String courseId ,
                            @RequestParam(value = "name_group") String name_group,
                            @RequestParam(value = "course_id") String course_id ,
                            @RequestParam(value = "day") String day ,
                            @RequestParam(value = "time") String time ,
                            @RequestParam(value = "teacher_id") int teacher_id,
                            @RequestParam(value = "room") String room,
                            @RequestParam(value = "quantity_student") int quantity_student){
        Account account = new Account();
        account.setAccount_id(teacher_id);
        Course course = new Course();
        course.setId(course_id);
        String thoigian = "Thứ "+ day +", kíp "+time;
        String groupId = course_id + "_" + String.format("N%02d",Integer.parseInt(name_group));
//        String groupId = course_id+"_"+String.format("N%02d", Integer.parseInt(name_group.substring(5)));
        String group_name = "Nhóm " + name_group;

        group.setGroupName(group_name);
        group.setGroupId(groupId);
        group.setCourse(course);
        group.setTime(thoigian);
        group.setTeacher(account);
        group.setRoom(room);
        group.setMaxStudents(quantity_student);
        group.setAvailableSlots(quantity_student);

        model.addAttribute("course_id",course_id);
        model.addAttribute("courseactive","active");

        groupService.saveGroup(group);
        return "redirect:/groupByCourse/{courseId}";
    }

    @GetMapping("/groupByCourse/{courseId}/{groupId}/edit")
    public String editGroup(@PathVariable("groupId") String groupId ,
                            Model model,
                            @PathVariable("courseId") String courseId){
        GroupDto groupDto = groupService.findGroupById(groupId);
        List<AccountDto> accounts = groupService.findAllTeacherAccount();
//        groupDto.setRegisted(true);
//        groupDto.setMaxStudents(20);
//        groupDto.setAvailableSlots(20);
//        model.addAttribute("blockDuplicate","block");
        model.addAttribute("accounts",accounts);
        model.addAttribute("groupId",groupId);
        model.addAttribute("group",groupDto);
        model.addAttribute("courseId",courseId);
        model.addAttribute("courseactive","active");
        return "edit_group";
    }

    @PostMapping("/groupByCourse/{courseId}/{groupId}/edit")
    public String updateGroup(@PathVariable(value = "groupId") String groupId ,
                              @PathVariable(value = "courseId") String courseId,
                              @ModelAttribute("group") GroupDto group,
                              @RequestParam(value = "name_group") String name_group,
                              @RequestParam(value = "course_id") String course_id ,
                              @RequestParam(value = "day") String day ,
                              @RequestParam(value = "time") String time ,
                              @RequestParam(value = "teacher_id") int teacher_id,
                              @RequestParam(value = "room") String room,
                              @RequestParam(value = "quantity_student") int quantity_student,
                              Model model){
        group.setGroupId(groupId);
        String thoigian = "Thứ "+day +", kíp " +time;
        Account account = new Account();
        account.setAccount_id(teacher_id);
        List<Group> list = groupService.getGroupByTeacherID(teacher_id);
        boolean checkTeacher = true;
        for(Group x : list ){
            if(x.getTime().equals(thoigian) && !x.getGroupId().equals(groupId) && teacher_id!=10){
                checkTeacher = false;
            }
            System.out.println("lay"+x.getTime());
        }
        System.out.println(checkTeacher);
//        account.setName(teacher_name);
        Course course = new Course();
        course.setId(course_id);

        group.setCourse(course);
        group.setTeacher(account);
        group.setGroupName(name_group);
        group.setRegisted(true);
        group.setMaxStudents(quantity_student);
        group.setAvailableSlots(quantity_student);
        group.setTime(thoigian);
        group.setRoom(room);

        model.addAttribute("courseactive","active");
        if(checkTeacher) {
            groupService.updateGroup(group);
            return "redirect:/groupByCourse/{courseId}";
        }
        model.addAttribute("blockDuplicate","block");
        return "edit_group";
//        return "redirect:/groupByCourse/{courseId}/{groupId}/edit";
    }

    @GetMapping("/groupByCourse/{courseId}/{groupId}/delete")
    public String deleteGroup(@PathVariable(value = "groupId") String groupId , @ModelAttribute("courseId") String courseId,Model model ){
        try {
            groupService.delete(groupId);
            model.addAttribute("courseactive","active");
            return "redirect:/groupByCourse/{courseId}";
        }catch (Exception x){
            model.addAttribute("notaction","block");
        }
        List<GroupDto> groups = groupService.getGroupsForCourse(courseId);
        List<AccountDto> accounts = groupService.findAllTeacherAccount();
        for(AccountDto accountDto : accounts){
            System.out.println(accountDto.getFullName());
        }
        Group group = new Group();

        model.addAttribute("accounts",accounts);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groups", groups);
        model.addAttribute("group",group);
        model.addAttribute("courseactive","active");
        return "group_manager";
    }

    @GetMapping("/groupByCourse/{courseId}/{groupId}/addGrade")
    public String addGrade(@PathVariable(value = "courseId") String courseId ,
                           Model model,
                           @PathVariable(value = "groupId") String groupId){
        List<GroupDto> groups = groupService.getGroupsForCourse(courseId);

        model.addAttribute("groups", groups);
        model.addAttribute("blockFilePath","block");
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("courseactive","active");
        return "group_manager";
    }

    @PostMapping("/groupByCourse/{courseId}/{groupId}/addGrade")
    public String test(Model model,
                       @RequestParam(value = "file") String filePath,
                       @PathVariable(value = "courseId") String courseId,
                       @PathVariable(value = "groupId") String groupId){
        model.addAttribute("file",filePath);
        List<CourseRegistrationDto> list =  new ArrayList<>();
        try {
            // Đường dẫn đến tệp Excelx
            System.out.println(filePath);
            String excelFilePath = filePath;
            if (excelFilePath.contains("\"")) {
                excelFilePath = excelFilePath.replace("\"", "");
            }
            System.out.println(excelFilePath);

            // Tạo một FileInputStream để đọc tệp Excel
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

            // Tạo một đối tượng Workbook từ FileInputStream
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // Chọn một sheet (ví dụ: sheet đầu tiên)
            Sheet sheet = workbook.getSheetAt(0);

            // Lặp qua từng hàng của sheet
            int dem = -1;
            for (Row row : sheet) {
                Integer accountId = null;
                Float diem = null;
                dem++;
                if(dem < 10) continue;
                int cot = 0;
                for (Cell cell : row) {
                    cot++;
                    if(cot == 2 && cell != null){
                        if (cell.getCellType() == CellType.NUMERIC) {
                            double numericValue = cell.getNumericCellValue();
                            // Kiểm tra xem giá trị có phải là số nguyên hay không
                            if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue)) {
                                // Giá trị là số nguyên, sử dụng String.format để in ra
                                accountId = Integer.parseInt(String.format(Locale.US, "%.0f ", numericValue).trim());
//                         System.out.print(String.format(Locale.US, "%.0f ", numericValue));
                            }
                        }
                    }
                    if(cot == 11){
                        try{
                            diem = Float.parseFloat(cell.toString());
//                            System.out.println(diem);
                        }catch(Exception e){

                        }
//                        diem = Float.parseFloat(cell.toString().trim());
                    }
                    if(cot > 11 ){
                        break;
                    }
                }
                if(accountId != null){
                    CourseRegistrationDto courseRegistrationDto = groupService.findCourseRegistration(courseId,accountId);
                    courseRegistrationDto.setGrade_10(diem);
                    groupService.updateCourseRigistation(courseRegistrationDto);
                    list.add(courseRegistrationDto);
                }
            }

            // Đóng FileInputStream và Workbook sau khi hoàn thành
            inputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(CourseRegistrationDto x : list){
            System.out.println(x.getAccount().getAccount_id()+" "+x.getCourse().getId() + " " + x.getGrade_10() + " " + x.getAccount().getName());
        }
        model.addAttribute("list",list);
        model.addAttribute("courseId",courseId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("courseactive","active");
        return "add_grade";

    }

    // testtttttttttttttt
//@PostMapping("/groupByCourse/{courseId}/{groupId}/addGrade")
//public String com.ptit.qldt.test(Model model, @RequestParam(value = "file") String filePath,@PathVariable(value = "courseId") String courseId){
//    model.addAttribute("file",filePath);
//    String list = "";
////                groupService.readExcel("C:\\Users\\ASUS\\Desktop\\"+filePath,courseId);
////        list += "duong";
////        List<CourseRegistrationDto> list = new ArrayList<>();
//    try {
////            String idCourse = "BAS1105";
//        // Đường dẫn đến tệp Excel
//        String excelFilePath = "C:\\Users\\ASUS\\Desktop\\"+filePath;
//
//        // Tạo một FileInputStream để đọc tệp Excel
//        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
//
//        // Tạo một đối tượng Workbook từ FileInputStream
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//
//        // Chọn một sheet (ví dụ: sheet đầu tiên)
//        Sheet sheet = workbook.getSheetAt(0);
//
//        // Lặp qua từng hàng của sheet
//        int dem = -1;
//        for (Row row : sheet) {
//            String accountId = null;
//            Float diem = null;
//            dem++;
//            if(dem <= 10) continue;
//            int cot = 0;
//            for (Cell cell : row) {
//                cot++;
//                    if(cell != null){
//                        if(cot==2 || cot == 3 || cot == 4 || cot == 5 || cot==6 || cot == 7 || cot == 8 || cot == 9 || cot ==10 || cot==11){
//                            list = list+" "+cell;
//                        }
//                    }
////                if(cot == 2 && cell != null){
////                    accountId = cell.toString();
////                }
////                if(cot == 11){
////                    diem = Float.parseFloat(cell.toString().trim());
////                }
//
////                CourseRegistrationDto courseRegistrationDto = groupService.findCourseRegistration(courseId,accountId);
////                list.add(courseRegistrationDto);
//                if(cot > 11 ){
//                    break;
//                }
//            }
//        }
//
//        // Đóng FileInputStream và Workbook sau khi hoàn thành
//        inputStream.close();
//        workbook.close();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
////    for(CourseRegistrationDto x : list){
////        System.out.println(x.getId());
////    }
//    model.addAttribute("list",list);
//    return "index";
//}

//    @PostMapping("/home/create-notification")
//    public String createNotification(@RequestParam("title") String title, @RequestParam("mes") String mes) {
//        notificationService.save(title, mes);
//        return "redirect:/home";
//    }


    @GetMapping("/getliststudents/{groupId}/getlist")
    public void exportExcel(HttpServletResponse response, @PathVariable String groupId, HttpSession session) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ListStudent.xlsx");

        Account user = (Account) session.getAttribute("acc");
        Group group = groupService.getGroupById(groupId);
        List<AccountDto> listStudent = userService.findStudentsByGroupId(groupId);

        try (OutputStream out = response.getOutputStream()) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            Font defaultFont = workbook.createFont();
            defaultFont.setFontName("Times New Roman");

            // Thiết lập font mặc định cho toàn bộ workbook
            CellStyle defaultStyle = workbook.createCellStyle();
            defaultStyle.setFont(defaultFont);
            for(int i=0; i<=10; i++) {
                sheet.setDefaultColumnStyle(i, defaultStyle);
            }

            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 15 * 256);

            // Tạo một CellStyle mới
            CellStyle style_b_14 = workbook.createCellStyle();
            // Thiết lập font in đậm
            Font font_b_14 = workbook.createFont();
            font_b_14.setBold(true); // In đậm
            font_b_14.setFontHeightInPoints((short) 14); // Cỡ chữ 14
            font_b_14.setFontName("Times New Roman");
            style_b_14.setFont(font_b_14);

            CellStyle style_b = workbook.createCellStyle();
            // Thiết lập font in đậm
            Font font_b = workbook.createFont();
            font_b.setBold(true); // In đậm
            font_b.setFontName("Times New Roman");
            style_b.setFont(font_b);


            CellStyle style_b_w = workbook.createCellStyle();
            // Thiết lập font in đậm
            Font font_b_w = workbook.createFont();
            font_b_w.setBold(true); // In đậm
            font_b_w.setFontName("Times New Roman");
            style_b_w.setFont(font_b_w);
            style_b_w.setWrapText(true);



            Row row0 = sheet.createRow(0);
            row0.createCell(0).setCellValue("HỌC VIỆN CÔNG NGHỆ BƯU CHÍNH VIỄN THÔNG");
            Cell cell0_0 = row0.getCell(0);
            cell0_0.setCellStyle(style_b);
            row0.createCell(7).setCellValue("BẢNG ĐIỂM THÀNH PHẦN");
            Cell cell0_7 = row0.getCell(7);
            cell0_7.setCellStyle(style_b_14);

            Row row1 = sheet.createRow(1);
            row1.createCell(1).setCellValue("KHOA:");
            row1.createCell(3).setCellValue("CƠ BẢN");

            Row row2 = sheet.createRow(2);
            row2.createCell(1).setCellValue("BỘ MÔN:");
            row2.createCell(3).setCellValue("CÔNG NGHỆ PHẦN MỀM");
            row2.createCell(7).setCellValue("Thi lần 1 học kỳ 1 năm học 2023 - 2024");

            Row row3 = sheet.createRow(3);
            row3.createCell(1).setCellValue("Học phần:");
            row3.createCell(3).setCellValue(group.getCourse().getName());
            row3.createCell(7).setCellValue("Nhóm:");
            row3.createCell(8).setCellValue(group.getGroupId());

            Row row4 = sheet.createRow(4);
            row4.createCell(1).setCellValue("Số tín chỉ:");
            row4.createCell(3).setCellValue(group.getCourse().getNum_credit());

            Row row8 = sheet.createRow(8);
            row8.createCell(0).setCellValue("STT");
            row8.createCell(1).setCellValue("Mã SV");
            row8.createCell(2).setCellValue("Họ và tên");
            row8.createCell(3).setCellValue("");
            row8.createCell(4).setCellValue("");
            row8.createCell(5).setCellValue("Điểm CC");
            row8.createCell(6).setCellValue("Điểm TBKT");
            row8.createCell(7).setCellValue("Điểm TH-TN");
            row8.createCell(8).setCellValue("Điểm BTTL");
            row8.createCell(9).setCellValue("Điểm thi");
            row8.createCell(10).setCellValue("Điểm TK");
            for(int i=0; i<=10; i++) {
                Cell cell8_i = row8.getCell(i);
                cell8_i.setCellStyle(style_b_w);
            }

            Row row9 = sheet.createRow(9);
            row9.createCell(0).setCellValue("");
            row9.createCell(1).setCellValue("");
            row9.createCell(2).setCellValue("Trọng số");
            row9.createCell(3).setCellValue("");
            row9.createCell(4).setCellValue("");
            row9.createCell(5).setCellValue(10);
            row9.createCell(6).setCellValue(10);
            row9.createCell(7).setCellValue(10);
            row9.createCell(8).setCellValue(0);
            row9.createCell(9).setCellValue(70);
            for(int i=0; i<10; i++) {
                Cell cell9_i = row9.getCell(i);
                cell9_i.setCellStyle(style_b);
            }


            int rowIndex = 10;

            int cnt = 1;
            for (AccountDto x : listStudent) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(cnt++);
                dataRow.createCell(1).setCellValue(x.getAccount_id());
                dataRow.createCell(2).setCellValue(x.getLastName());
                dataRow.createCell(3).setCellValue(x.getFirstName());
            }



            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
