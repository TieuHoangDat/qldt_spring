package com.ptit.qldt.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class test {

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Thay đổi đường dẫn tùy thuộc vào thư mục mà bạn muốn lưu trữ tệp
                String filePath = "./" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
                return "redirect:/success";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/error";
    }
}
