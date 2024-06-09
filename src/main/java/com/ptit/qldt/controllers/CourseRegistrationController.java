package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.TermDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.CourseRegistration;
import com.ptit.qldt.services.CourseRegistrationService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.CourseRegistrationMapper.mapToCourseRegistrationDto;

@Controller
public class CourseRegistrationController {
    private CourseRegistrationService courseRegistrationService;
    @Autowired
    public CourseRegistrationController(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    @GetMapping("/show_grade")
    public String showGrade(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("acc");

        int id = user.getAccount_id();
        List<String> listTermName = courseRegistrationService.getTermByStudentID(id);
        List<TermDto> listTerm = new ArrayList<>();

        for(String s : listTermName) {
            List<CourseRegistration> tmp = courseRegistrationService.getCRByIdAndTerm(id, s);
            TermDto t = new TermDto(s, tmp.stream().map(x -> mapToCourseRegistrationDto(x)).collect(Collectors.toList()));
            if(!t.getTerm().equals("Kỳ 2 năm học 2023-2024")){
                listTerm.add(t);
            }else{
                System.out.println("Thôi");
            }

        }
        Collections.sort(listTerm);
        double tl_10 = 0, tl_4 = 0;
        int tl = 0;

        for(TermDto t : listTerm) {
            tl_10 += t.getAvg_10() * t.getTotal_credit();
            tl_4 += t.getAvg_4() * t.getTotal_credit();
            tl += t.getTotal_credit();
            t.setTl_10(Math.round((tl_10 / tl)*100.0) / 100.0);
            t.setTl_4(Math.round((tl_4 / tl)*100.0) / 100.0);
            t.setTl_credit(tl);
        }

//        request.setAttribute("gradeactive", "active");
        model.addAttribute("listT", listTerm);
        model.addAttribute("gradeactive","active");

        for(TermDto x : listTerm){
            System.out.println(x.getTerm());
        }

        return "show_grade";
    }

    @GetMapping("/tuition")
    public String showTuition(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("acc");

        int id = user.getAccount_id();
        List<String> listTermName = courseRegistrationService.getTermByStudentID(id);
        List<TermDto> listTerm = new ArrayList<>();

        for(String s : listTermName) {
            List<CourseRegistration> tmp = courseRegistrationService.getCRByIdAndTerm(id, s);
            TermDto t = new TermDto(s, tmp.stream().map(x -> mapToCourseRegistrationDto(x)).collect(Collectors.toList()));
            listTerm.add(t);
        }
        Collections.sort(listTerm);

        int total_credit = 0;
        int total_tuition = 0;

        for(TermDto t : listTerm) {
            total_credit += t.getTotal_credit();
            total_tuition += t.getTuition();
        }

//        request.setAttribute("gradeactive", "active");
        model.addAttribute("listT", listTerm);
        model.addAttribute("total_credit", total_credit);
        model.addAttribute("total_tuition", total_tuition);
        model.addAttribute("tuitionactive","active");

        return "tuition";
    }

    @GetMapping("/exportGrade")
    public void exportExcel(HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Grade.xlsx");

        Account user = (Account) session.getAttribute("acc");

        int id = user.getAccount_id();
        List<String> listTermName = courseRegistrationService.getTermByStudentID(id);
        List<TermDto> listTerm = new ArrayList<>();

        for(String s : listTermName) {
            List<CourseRegistration> tmp = courseRegistrationService.getCRByIdAndTerm(id, s);
            TermDto t = new TermDto(s, tmp.stream().map(x -> mapToCourseRegistrationDto(x)).collect(Collectors.toList()));
            listTerm.add(t);
        }
        Collections.sort(listTerm);
        double tl_10 = 0, tl_4 = 0;
        int tl = 0;

        for(TermDto t : listTerm) {
            tl_10 += t.getAvg_10() * t.getTotal_credit();
            tl_4 += t.getAvg_4() * t.getTotal_credit();
            tl += t.getTotal_credit();
            t.setTl_10(Math.round((tl_10 / tl)*100.0) / 100.0);
            t.setTl_4(Math.round((tl_4 / tl)*100.0) / 100.0);
            t.setTl_credit(tl);
        }

        try (OutputStream out = response.getOutputStream()) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            Row row0 = sheet.createRow(2);
            row0.createCell(0).setCellValue("Bảng điểm của sinh viên: " + user.getName());

            int rowIndex = 4;

            sheet.setColumnWidth(1, 20 * 256);

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

            for(TermDto t : listTerm) {
                Row row1 = sheet.createRow(rowIndex);
                row1.createCell(0).setCellValue(t.getTerm());

                Cell cell0 = row1.getCell(0);
                cell0.setCellStyle(style_b);
                rowIndex++;

                Row headerRow = sheet.createRow(rowIndex++);
                headerRow.createCell(0).setCellValue("Mã môn học");
                headerRow.createCell(1).setCellValue("Tên môn học");
                headerRow.createCell(2).setCellValue("Số tín chỉ");
                headerRow.createCell(3).setCellValue("Điểm TK(10)");
                headerRow.createCell(4).setCellValue("Điểm TK(4)");
                headerRow.createCell(5).setCellValue("Điểm TK(C)");
                for(int i=0; i<=5; i++) {
                    Cell cell = headerRow.getCell(i);
                    cell.setCellStyle(style_b_w);
                }

                for (CourseRegistrationDto x : t.getLi()) {
                    Row dataRow = sheet.createRow(rowIndex++);
                    dataRow.createCell(0).setCellValue(x.getCourse().getId());
                    dataRow.createCell(1).setCellValue(x.getCourse().getName());
                    dataRow.createCell(2).setCellValue(x.getCourse().getNum_credit());
                    dataRow.createCell(3).setCellValue(x.getGrade_10());
                    dataRow.createCell(4).setCellValue(x.getGrade_4());
                    dataRow.createCell(5).setCellValue(x.getGrade_a());
                }
                Row row2 = sheet.createRow(rowIndex++);
                row2.createCell(0).setCellValue("- Điểm trung bình học kỳ hệ 4: " + t.getAvg_4());
                row2.createCell(4).setCellValue("- Điểm trung bình tích lũy hệ 4: " + t.getTl_4());

                Row row3 = sheet.createRow(rowIndex++);
                row3.createCell(0).setCellValue("- Điểm trung bình học kỳ hệ 10: " + t.getAvg_10());
                row3.createCell(4).setCellValue("- Điểm trung bình tích lũy hệ 10: " + t.getTl_10());

                Row row4 = sheet.createRow(rowIndex++);
                row4.createCell(0).setCellValue("- Số tín chỉ đạt học kỳ: " + t.getTotal_credit());
                row4.createCell(4).setCellValue("- Số tín chỉ tích lũy: " + t.getTl_credit());

                rowIndex++;
            }

            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
