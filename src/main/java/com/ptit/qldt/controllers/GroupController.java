package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.services.GroupService;
import com.ptit.qldt.services.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


@Controller
public class GroupController {
    private GroupService groupService;
    private UserService userService;
    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }


    @GetMapping("/group_teacher")
    public String showTimeTable(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("acc");
        List<Group> listg = groupService.getGroupByTeacherID(user.getAccount_id());
        model.addAttribute("groups", listg);
        return "group_teacher";
    }

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
